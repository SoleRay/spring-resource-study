package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public Object getBean(String beanName) {
        Object bean = beanMap.get(beanName);
        if (bean == null) {
            bean = doCreateBean(beanName);
        }

        return bean;
    }

    private Object doCreateBean(String beanName) {
        Object bean = null;

        BeanDefinition bd = beanDefinitionMap.get(beanName);

        if (bd == null) {
            return null;
        }


        if (bd.getFactoryMethodName() == null) {
            bean = createInstanceByConstructor(bd);
        } else {
            String factoryBeanName = bd.getFactoryBeanName();
            if (factoryBeanName == null) {
                bean = createInstanceByStaticFactoryMethod(bd);
            } else {
                bean = createInstanceByBeanFactory(bd);
            }
        }

        return bean;
    }


    private Object createInstanceByConstructor(BeanDefinition bd) {
        try {
            //根据bean定义有没有构造器参数来决定使用无参构造，还是有参数的构造
            if (bd.getConstructorArgs() == null) {
                return bd.getBeanType().getConstructor().newInstance();
            } else {
                List<Object> realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());
                //创建对象
                return determineConstructor(bd, realArgs).newInstance(realArgs);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Constructor determineConstructor(BeanDefinition bd, List<Object> realArgs) {

        if (realArgs == null) {
            try {
                return bd.getBeanType().getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Class[] argTypes = getRealArgTypes(realArgs);

        Constructor constructor = null;
        try {
            constructor = bd.getBeanType().getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (constructor == null) {

            Constructor[] constructors = bd.getBeanType().getConstructors();

            outer:
            for (Constructor c : constructors) {

                if (c.getParameterCount() != argTypes.length) {
                    continue;
                }

                Class[] parameterTypes = c.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].isAssignableFrom(argTypes[i])) {
                        continue outer;
                    }
                }

                constructor = c;
                break;
            }

        }
        return constructor;
    }

    private Class[] getRealArgTypes(List<Object> realArgs) {
        if (realArgs == null) {
            return null;
        }

        Class[] argTypes = new Class[realArgs.size()];
        for (int i = 0; i < realArgs.size(); i++) {
            argTypes[i] = realArgs.get(i).getClass();
        }
        return argTypes;
    }

    private List<Object> getRealValueForConstructorArgs(List<Object> constructorArgs) {

        if (constructorArgs == null) {
            return null;
        }

        List<Object> realValues = new ArrayList<>();

        Object realValue = null;
        for (Object arg : constructorArgs) {
            if (arg == null) {
                throw new IllegalArgumentException("构造参数不能为空！");
            }

            if (arg instanceof BeanReference) {
                realValue = getBean(((BeanReference) arg).getBeanName());
            } else if (arg instanceof Map) {
            } else if (arg instanceof List) {
            } else {
                realValue = arg;
            }

            realValues.add(realValue);
        }
        return realValues;
    }

    private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) {

        Class beanType = bd.getBeanType();

        //根据bean定义有没有构造器参数来决定使用无参构造，还是有参数的构造

        try {
            if (bd.getConstructorArgs() == null) {
                return bd.getBeanType().getMethod(bd.getFactoryMethodName()).invoke(beanType);

            } else {
                //找到真实的参数值
                List<Object> realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());

                //根据参数找到工厂方法
                Method m = determineFactoryMethod(bd, bd.getBeanType(), realArgs);
                return m.invoke(beanType);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method determineFactoryMethod(BeanDefinition bd,Class<?> beanType, List<Object> realArgs) {
        if(beanType==null){
            beanType = bd.getBeanType();
        }

        Class[] argTypes = getRealArgTypes(realArgs);

        Method method = null;
        try {
            method = beanType.getMethod(bd.getFactoryMethodName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (method == null) {

            Method[] methods = beanType.getMethods();

            outer:
            for (Method m : methods) {

                if (m.getParameterCount() != argTypes.length) {
                    continue;
                }

                Class[] parameterTypes = m.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].isAssignableFrom(argTypes[i])) {
                        continue outer;
                    }
                }

                method = m;
                break;
            }

        }
        return method;
    }

    private Object createInstanceByBeanFactory(BeanDefinition bd) {

        try {
            String factoryBeanName = bd.getFactoryBeanName();
            Object factoryBean = getBean(factoryBeanName);
            if (bd.getConstructorArgs() == null) {

                return factoryBean.getClass().getMethod(bd.getFactoryMethodName()).invoke(factoryBean);

            } else {
                List<Object> realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());
                Method m = determineFactoryMethod(bd,factoryBean.getClass(), realArgs);
                return m.invoke(factoryBean);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void registerBeanDefition(String beanName, BeanDefinition bd) throws Exception {
        if (beanDefinitionMap.containsKey(beanName)) {
            throw new Exception("bean已经存在");
        }

        beanDefinitionMap.put(beanName, bd);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
