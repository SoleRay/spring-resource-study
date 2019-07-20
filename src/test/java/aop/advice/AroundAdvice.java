package aop.advice;

import java.lang.reflect.Method;

public class AroundAdvice implements MethodAroundAdvice {

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        System.out.println("AroundAdvice before");
        Object returnValue = method.invoke(target, args);
        System.out.println("AroundAdvice after get returnValue="+returnValue);
        return returnValue;
    }
}
