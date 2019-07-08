package factory;

import java.lang.reflect.Constructor;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition{

    private String beanName;

    private Class beanType;

    private String scope;

    private String initMethodName;

    private String destroyMethodName;

    private String factoryBeanName;

    private String factoryMethodName;

    private List<Object> constructorArgs;

    private Constructor constructor;

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public Class getBeanType() {
        return beanType;
    }

    @Override
    public void setBeanType(Class beanType) {
        this.beanType = beanType;
    }

    @Override
    public void setBeanType(String className) {
        try {
            this.beanType = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getInitMethodName() {
        return initMethodName;
    }

    @Override
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    @Override
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    @Override
    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    @Override
    public List<Object> getConstructorArgs() {
        return constructorArgs;
    }

    @Override
    public void setConstructorArgs(List<Object> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    @Override
    public Constructor getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }
}
