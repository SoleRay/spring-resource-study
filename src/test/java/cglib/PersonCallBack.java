package cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class PersonCallBack implements MethodInterceptor {

    private Object target;

    public PersonCallBack(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        doSomethingBefore();
        method.invoke(target,objects);
        System.out.println("end...");
        return null;
    }


    private void doSomethingBefore() {
        System.out.println("think...");
    }

    private void doSomethingAfter() {
        System.out.println("summarize...");
    }
}
