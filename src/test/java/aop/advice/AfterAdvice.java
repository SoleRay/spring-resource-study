package aop.advice;

import java.lang.reflect.Method;

public class AfterAdvice implements MethodAfterAdvice {

    @Override
    public void after(Object target, Method method, Object[] args, Object returnValue) {
        System.out.println("AfterAdvice get returnValue=" + returnValue);
    }
}
