package aop.proxy;

import aop.advisor.Advisor;
import factory.BeanFactory;

import java.util.List;

public interface AopProxyFactory {

    Object createAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisors, BeanFactory beanFactory);

    static AopProxyFactory createDefaultAopProxyFactory(){
        return new DefaultAopProxyFactory();
    }
}
