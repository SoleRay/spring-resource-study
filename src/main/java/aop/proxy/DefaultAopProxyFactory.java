package aop.proxy;

import aop.advisor.Advisor;
import factory.BeanFactory;

import java.util.List;

public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public Object createAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisors, BeanFactory beanFactory) {
        Object proxy = null;

        if(shouldUseJDKDynamicProxy(bean,beanName)){
            proxy = new JdkDynamicAopProxy(beanName,bean,matchedAdvisors,beanFactory).getProxy();
        }else {
            proxy = new CglibDynamicAopProxy(beanName,bean,matchedAdvisors,beanFactory).getProxy();
        }
        return proxy;
    }

    private boolean shouldUseJDKDynamicProxy(Object bean, String beanName) {

        return false;
    }
}
