package aop.pointcut;

import java.lang.reflect.Method;

public interface Pointcut {

    boolean matchesClass(Class<?> targetClass);

    boolean matchesMethod(Method method, Class<?> targetClass);

    String getExpression();
}
