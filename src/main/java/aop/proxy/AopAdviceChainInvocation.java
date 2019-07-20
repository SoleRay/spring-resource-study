package aop.proxy;

import aop.advice.Advice;
import aop.advice.MethodAfterAdvice;
import aop.advice.MethodAroundAdvice;
import aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.List;

public class AopAdviceChainInvocation {

    private static Method invokeMethod;

    static {
        try {
            invokeMethod = AopAdviceChainInvocation.class.getDeclaredMethod("invoke");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Object target;

    private Method method;

    private Object[] args;

    private List<Advice> shouldApplyAdvices;

    private int index;

    public AopAdviceChainInvocation(Object target, Method method, Object[] args, List<Advice> shouldApplyAdvices) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.shouldApplyAdvices = shouldApplyAdvices;
    }

    public Object invoke() throws Throwable {

        if(index < shouldApplyAdvices.size()){
            Advice advice = shouldApplyAdvices.get(index++);
            if (advice instanceof MethodBeforeAdvice) {
                MethodBeforeAdvice beforeAdvice = (MethodBeforeAdvice) advice;
                beforeAdvice.before(target, method, args);

            } else if (advice instanceof MethodAroundAdvice) {
                MethodAroundAdvice aroundAdvice = (MethodAroundAdvice) advice;
                return aroundAdvice.invoke(this, invokeMethod, null);
            } else if (advice instanceof MethodAfterAdvice) {
                MethodAfterAdvice afterAdvice = (MethodAfterAdvice) advice;
                Object returnValue = this.invoke();
                afterAdvice.after(target, method, args, returnValue);
                return returnValue;
            }
            return this.invoke();
        }else {
            return method.invoke(target,args);
        }
    }
}
