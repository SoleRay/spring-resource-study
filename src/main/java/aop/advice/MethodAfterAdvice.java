package aop.advice;

import java.lang.reflect.Method;

public interface MethodAfterAdvice extends Advice{

    void after(Object target, Method method, Object[] args,Object returnValue);
}
