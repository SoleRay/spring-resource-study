package aop.proxy;

import aop.advisor.Advisor;
import factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private String beanName;

    private Object target;

    private List<Advisor> matchedAdvisors;

    private BeanFactory beanFactory;

    public JdkDynamicAopProxy(String beanName,Object target, List<Advisor> matchedAdvisors, BeanFactory beanFactory) {
        this.beanName = beanName;
        this.target = target;
        this.matchedAdvisors = matchedAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return AopProxyUtils.applyAdvice(target,method,args,matchedAdvisors,beanFactory,proxy);
    }
}
