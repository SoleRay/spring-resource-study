package aop.bean;

import aop.advisor.Advisor;
import aop.advisor.AdvisorRegistry;
import aop.advisor.PointcutAdvisor;
import aop.pointcut.Pointcut;
import aop.proxy.AopProxy;
import aop.proxy.AopProxyFactory;
import factory.BeanFactory;
import factory.BeanFactoryAware;
import org.apache.commons.collections4.CollectionUtils;
import utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AdvisorAutoProxyCreator implements BeanPostProcessor, AdvisorRegistry, BeanFactoryAware {


    private List<Advisor> advisors;

    private BeanFactory beanFactory;

    public AdvisorAutoProxyCreator() {
        advisors = new ArrayList<>();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        List<Advisor> matchedAdvisors = getMatchedAdvisors(bean.getClass());

        if(!CollectionUtils.isEmpty(matchedAdvisors)){
             bean = creatAopProxy(bean, beanName, matchedAdvisors);
        }

        return bean;

    }

    private List<Advisor> getMatchedAdvisors(Class<?> beanClass) {
        List<Method> methods = getAllMethods(beanClass);


        List<Advisor> matchedAdvisors = new ArrayList<>();
        for(Advisor advisor : advisors){

            if(advisor instanceof PointcutAdvisor ){
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                Pointcut pointcut = pointcutAdvisor.getPointcut();

                if(isPointcutBean(pointcut,beanClass,methods)){
                    matchedAdvisors.add(advisor);
                }
            }
        }
        return matchedAdvisors;
    }

    private List<Method> getAllMethods(Class<?> c) {
        return ReflectionUtils.getAllDeclaredMethods(c);
    }

    private boolean isPointcutBean(Pointcut pointcut, Class<?> beanClass, List<Method> methods) {

        //判断类是否属于切入点（主要是过滤，如果类都不符合，那么方法就不用看了）
        if(!pointcut.matchesClass(beanClass)){
            return false;
        }

        //判断方法是否属于切入点
        for(Method method : methods){
            if(pointcut.matchesMethod(method,beanClass)){
                return true;
            }
        }

        return false;
    }

    private Object creatAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisors) {
       return AopProxyFactory.createDefaultAopProxyFactory().createAopProxy(bean,beanName,matchedAdvisors,beanFactory);
    }


    @Override
    public void registerAdvisor(Advisor ad) {
        advisors.add(ad);
    }

    @Override
    public List<Advisor> getAdvisors() {
        return advisors;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
