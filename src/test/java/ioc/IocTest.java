package ioc;

import factory.DefaultBeanFactory;
import ioc.test.constructor.ConstructorIocTest;
import ioc.test.factory.FactoryIocTest;
import ioc.test.loop.LoopIocTest;
import ioc.test.method.StaticFactoryMethodIocTest;

public class IocTest {

    public static void main(String[] args) throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();

        //无参构造
        ConstructorIocTest.testDefaultConstructor(factory);
        StaticFactoryMethodIocTest.testDefaultStaticFactoryMethod(factory);
        FactoryIocTest.testDefaultFactoryBean(factory);

        //带构造参数
        ConstructorIocTest.testArgumentConstructor(factory);
        StaticFactoryMethodIocTest.testArgumentsStaticFactoryMethod(factory);
        FactoryIocTest.testArgumentsFactoryBean(factory);

        //循环依赖测试
        LoopIocTest.testLoopBean(factory);
    }












}
