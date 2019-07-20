package factory;

import aop.bean.BeanPostProcessor;

public interface BeanFactory {

    Object getBean(String beanName) throws Exception;

    void registerBeanPostProcessor(BeanPostProcessor processor);
}
