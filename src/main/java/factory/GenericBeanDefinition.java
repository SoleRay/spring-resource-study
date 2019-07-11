package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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

    private Method factoryMethod;

    private List<PropertyValue> propertyValues;

    @Override
    public boolean isSingleton() {
        return BeanDefinition.SCOPE_SINGLETON.equals(this.scope);
    }

    @Override
    public boolean isPrototype() {
        return BeanDefinition.SCOPE_PROTOTYPE.equals(this.scope);
    }

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

    @Override
    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public Method getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public void setFactoryMethod(Method factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    @Override
    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
