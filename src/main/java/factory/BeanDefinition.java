package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public interface BeanDefinition {

    String SCOPE_SINGLETON = "SINGLETON";

    String SCOPE_PROTOTYPE = "PROTOTYPE";

    String getBeanName();

    void setBeanName(String beanName);

    Class getBeanType();

    void setBeanType(Class beanType);

    void setBeanType(String className);

    String getScope();

    void setScope(String scope);


    String getInitMethodName();

    void setInitMethodName(String initMethodName);

    String getDestroyMethodName();

    void setDestroyMethodName(String destroyMethodName);


    String getFactoryBeanName();

    void setFactoryBeanName(String factoryBeanName);

    String getFactoryMethodName();

    void setFactoryMethodName(String factoryMethodName);

    List<Object> getConstructorArgs();

    void setConstructorArgs(List<Object> constructorArgs);

    Constructor<?> getConstructor();

    void setConstructor(Constructor<?> constructor);

    Method getFactoryMethod();

    void setFactoryMethod(Method factoryMethod);
}
