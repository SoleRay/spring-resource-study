package aop.advice;

import java.lang.reflect.Method;

public class BeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Object target, Method method, Object[] args) {
        System.out.println("BeforeAdvice....");
    }
}
