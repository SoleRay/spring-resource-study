package aop.advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice{

    void before(Object target, Method method, Object[] args);
}
