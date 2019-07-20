package aop.proxy;

import aop.advisor.Advisor;
import factory.BeanFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    private String beanName;

    private Object target;

    private List<Advisor> matchedAdvisors;

    private BeanFactory beanFactory;

    public CglibDynamicAopProxy(String beanName, Object target, List<Advisor> matchedAdvisors, BeanFactory beanFactory) {
        this.beanName = beanName;
        this.target = target;
        this.matchedAdvisors = matchedAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return AopProxyUtils.applyAdvice(target,method,objects,matchedAdvisors,beanFactory,o);
    }
}
