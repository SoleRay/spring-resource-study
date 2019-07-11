package factory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

    /**用来判断，创建新对象时是否存在循环依赖：即A的构造参数中有B对象，B的构造参数中又有A对象*/
    private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

    public DefaultBeanFactory() {
        HashSet<String> buildingBeanSet = new HashSet<>();
        buildingBeans.set(buildingBeanSet);
    }


    public Object getBean(String beanName) throws Exception {

        return doGetBean(beanName);

    }

    private Object doGetBean(String beanName) throws Exception {
        BeanDefinition bd = beanDefinitionMap.get(beanName);
        if (bd == null) {
            throw new Exception("do not find the BeanDefinition for" + beanName);
        }

        /**单例的话先从map中找，非单例的话，每次都新创建*/
        if (bd.isSingleton()) {
            Object bean = beanMap.get(bd);

            if (bean != null) {
                return bean;
            } else {
                return this.doCreateBean(bd);
            }
        } else {
            return this.doCreateBean(bd);
        }
    }

    private Object doCreateBean(BeanDefinition bd) throws Exception {
        Object instance = null;

        /**如果在创建A对象时，发现A对象正在创建中，说明创建A对象的时候，需要先创建B对象，但是创建B对象的时候，又需要创建A对象*/
        if (buildingBeans.get().contains(bd.getBeanName())) {
            throw new Exception("loop dependency...");
        }

        buildingBeans.get().add(bd.getBeanName());

        /**
         * 1.beanType不为空的话，如果没有工厂方法，那么就是用构造器直接创建：A a = new A()..
         * 2.beanType不为空的话，如果有工厂方法，说明是静态工厂方法创建： A a = A.getInstance();[static]
         * 3.beanType为空的话，说明是通过创建工厂，来创建实例：Factory f = new Factory(); A a = f.createA()
         */
        if (bd.getBeanType() != null) {
            if (StringUtils.isBlank(bd.getFactoryMethodName())) {
                instance = createInstanceByConstructor(bd);
            } else {
                instance = createInstanceByStaticFactoryMethod(bd);
            }
        } else {
            instance = createInstanceByBeanFactory(bd);
        }

        buildingBeans.get().remove(bd.getBeanName());

        /**依赖注入*/
        injectDependencies(bd, instance);

        if (bd.isSingleton()) {
            beanMap.put(bd.getBeanName(), instance);
        }

        return instance;
    }

    private Object createInstanceByConstructor(BeanDefinition bd) throws Exception {
        //根据bean定义有没有构造器参数来决定使用无参构造，还是有参数的构造
        if (bd.getConstructorArgs() == null) {
            return bd.getBeanType().getConstructor().newInstance();
        } else {
            Object[] realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());
            //创建对象
            return determineConstructor(bd, realArgs).newInstance(realArgs);
        }
    }

    private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws Exception {

        Class beanType = bd.getBeanType();

        //根据bean定义有没有构造器参数来决定使用无参构造，还是有参数的构造

        if (bd.getConstructorArgs() == null) {
            Method m = bd.getBeanType().getMethod(bd.getFactoryMethodName());
            return m.invoke(beanType);

        } else {
            //找到真实的参数值
            Object[] realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());

            //根据参数找到工厂方法
            Method m = determineFactoryMethod(bd, bd.getBeanType(), realArgs);
            return m.invoke(beanType, realArgs);
        }
    }

    private Object createInstanceByBeanFactory(BeanDefinition bd) throws Exception {

        String factoryBeanName = bd.getFactoryBeanName();
        Object factoryBean = getBean(factoryBeanName);
        if (bd.getConstructorArgs() == null) {
            Method m = factoryBean.getClass().getMethod(bd.getFactoryMethodName());
            return m.invoke(factoryBean);

        } else {
            Object[] realArgs = getRealValueForConstructorArgs(bd.getConstructorArgs());
            Method m = determineFactoryMethod(bd, factoryBean.getClass(), realArgs);
            return m.invoke(factoryBean, realArgs);
        }
    }

    /**
     * Bean定义的时候，构造器如果有参数，定义的参数是一种包裹类型的参数
     * 1.如果参数是基本类型，可以直接定义类型(int,long,boolean)，反射时可以直接注入
     * 2.如果参数是JDK内置引用类型（Map,List,Set),也可直接定义类型，反射时可以直接创建内置类型的对象，然后注入
     * 3.如果参数是自定义的引用类型（Bird,Car,Wheel）,则需要用到BeanReference.反射时需要根据BeanReference创建自定义对象，才能注入
     *
     * 正因为情况3的存在，所以才需要使用包裹类型(因为这种类不能直接创建，是在客户端调用的时候根据客户端的定义来创建的)。
     * getRealValueForConstructorArgs的目的，就是将包裹类型的参数值，转变成真实类型的参数值：
     * 1.int -> int
     * 2.Map -> Map
     * 3.BeanReference -> Bird
     */
    private Object[] getRealValueForConstructorArgs(List<Object> constructorArgs) throws Exception {

        if (constructorArgs == null) {
            return null;
        }

        Object[] realValues = new Object[constructorArgs.size()];

        Object realValue = null;
        for (int i = 0; i < constructorArgs.size(); i++) {
            Object arg = constructorArgs.get(i);
            if (arg == null) {
                throw new IllegalArgumentException("构造参数不能为空！");
            }

            realValue = findRealValueByType(realValue, arg);
            realValues[i] = realValue;
        }
        return realValues;
    }

    private Object findRealValueByType(Object realValue, Object wrappedValue) throws Exception {
        if (wrappedValue instanceof BeanReference) {
            String beanName = ((BeanReference) wrappedValue).getBeanName();

            realValue = getBean(beanName);
        } else if (wrappedValue instanceof Map) {
        } else if (wrappedValue instanceof List) {
        } else {
            realValue = wrappedValue;
        }
        return realValue;
    }

    /**
     * 根据参数来寻找构造器
     * 1.先根据参数精确查找构造器
     * 2.如果1找不到，那么取出目标类的全部构造器，先比对参数数目，再依次比较参数类型。
     *
     * 为什么1可能会找不到：
     * 构造器的形参定义可能是接口、父类或者基本类型的封装类，但实参传入的，可能是子类或者基本类型
     * 例如：Car(int color, Wheel w),实际传入的参数可能是（Integer, BlackWheel）
     */
    private Constructor determineConstructor(BeanDefinition bd, Object[] realArgs) {

        if (bd.getConstructor() != null) {
            return bd.getConstructor();
        }

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
        if (bd.getScope() == BeanDefinition.SCOPE_PROTOTYPE) {
            bd.setConstructor(constructor);
        }

        return constructor;
    }

    /**
     * 这个和构造器的寻找是一样的
     */
    private Method determineFactoryMethod(BeanDefinition bd, Class<?> beanType, Object[] realArgs) {

        if (bd.getFactoryMethod() != null) {
            return bd.getFactoryMethod();
        }

        if (beanType == null) {
            beanType = bd.getBeanType();
        }

        Class[] argTypes = getRealArgTypes(realArgs);

        Method method = null;
        try {
            method = beanType.getMethod(bd.getFactoryMethodName(), argTypes);
        } catch (NoSuchMethodException e) {
            System.out.println("can't find such method directly...will list all to match...");
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
                    if (!ClassUtils.isAssignable(parameterTypes[i], argTypes[i])) {
                        continue outer;
                    }
                }

                method = m;
                break;
            }

        }
        if (bd.getScope() == BeanDefinition.SCOPE_PROTOTYPE) {
            bd.setFactoryMethod(method);
        }

        return method;
    }

    private Class[] getRealArgTypes(Object[] realArgs) {
        if (realArgs == null) {
            return null;
        }

        Class[] argTypes = new Class[realArgs.length];
        for (int i = 0; i < realArgs.length; i++) {
            argTypes[i] = realArgs[i].getClass();
        }
        return argTypes;
    }

    /**
     * 注意：通过Field的set方法来注入，即使Bean没有定义set和get方法，也是可以的。
     */
    private void injectDependencies(BeanDefinition bd, Object bean) throws Exception {

        if (bean == null || CollectionUtils.isEmpty(bd.getPropertyValues())) {
            return;
        }

        List<PropertyValue> propertyValues = bd.getPropertyValues();

        Class beanType = bd.getBeanType();


        for (PropertyValue pv : propertyValues) {
            Field f = null;
            try {
                f = beanType.getDeclaredField(pv.getName());
            } catch (NoSuchFieldException e) {
                System.out.println("no such field" + pv.getName());
                continue;
            }
            f.setAccessible(true);

            Object realValue = null;

            Object pvValue = pv.getValue();
            realValue = findRealValueByType(realValue, pvValue);

            try {
                f.set(bean, realValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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
