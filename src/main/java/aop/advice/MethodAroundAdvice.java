package aop.advice;

import java.lang.reflect.Method;

public interface MethodAroundAdvice extends Advice {

    Object invoke(Object target, Method method, Object[] args) throws Throwable;;
}
