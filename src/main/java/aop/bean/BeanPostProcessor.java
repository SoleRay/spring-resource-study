package aop.bean;

public interface BeanPostProcessor {

    Object postProcessAfterInitialization(Object bean, String beanName);
}
