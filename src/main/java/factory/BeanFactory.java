package factory;

public interface BeanFactory<T> {

    T getBean(String beanName);
}
