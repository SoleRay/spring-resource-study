package aop.proxy;

import aop.advice.Advice;
import aop.advisor.Advisor;
import aop.advisor.PointcutAdvisor;
import factory.BeanFactory;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopProxyUtils {


    public static Object applyAdvice(Object target, Method method, Object[] args, List<Advisor> matchedAdvisors, BeanFactory beanFactory, Object proxy) throws Throwable {
        List<Advice> shouldApplyAdvices = getShouldApplyAdvices(target, method, matchedAdvisors, beanFactory);

        if(CollectionUtils.isEmpty(shouldApplyAdvices)){
            return method.invoke(target,args);
        }else {
            AopAdviceChainInvocation invocation = new AopAdviceChainInvocation(target,method,args,shouldApplyAdvices);
            return invocation.invoke();
        }
    }

    private static List<Advice> getShouldApplyAdvices(Object target, Method method, List<Advisor> matchedAdvisors, BeanFactory beanFactory) throws Exception {

        if(CollectionUtils.isEmpty(matchedAdvisors)){
            return null;
        }

        List<Advice> adviceList = new ArrayList<>();

        for(Advisor advisor : matchedAdvisors){
            if(advisor instanceof PointcutAdvisor){
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if(pointcutAdvisor.getPointcut().matchesMethod(method,target.getClass())){

                    Advice advice = (Advice) beanFactory.getBean(pointcutAdvisor.getAdviceBeanName());
                    adviceList.add(advice);
                }
            }
        }

        return adviceList;
    }
}
