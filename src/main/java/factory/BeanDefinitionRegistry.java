package factory;

import factory.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefition(String beanName, BeanDefinition bd) throws Exception;

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);
}
