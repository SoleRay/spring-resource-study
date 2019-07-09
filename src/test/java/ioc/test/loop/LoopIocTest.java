package ioc.test.loop;

import factory.BeanReference;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;

import java.util.ArrayList;
import java.util.List;

public class LoopIocTest {

    public static void testLoopBean(DefaultBeanFactory factory) throws Exception {
        String loopBeanAName = "loopBeanA";
        String loopBeanBName = "loopBeanB";

        GenericBeanDefinition loopBeanA = new GenericBeanDefinition();
        loopBeanA.setBeanName(loopBeanAName);
        loopBeanA.setBeanType("ioc.bean.loop.LoopBeanA");
        List<Object> argsLoopBeanA = new ArrayList<>();
        argsLoopBeanA.add(new BeanReference(loopBeanBName));
        loopBeanA.setConstructorArgs(argsLoopBeanA);

        GenericBeanDefinition loopBeanB = new GenericBeanDefinition();
        loopBeanB.setBeanName(loopBeanBName);
        loopBeanB.setBeanType("ioc.bean.loop.LoopBeanB");
        List<Object> argsLoopBeanB = new ArrayList<>();
        argsLoopBeanB.add(new BeanReference(loopBeanAName));
        loopBeanB.setConstructorArgs(argsLoopBeanB);

        factory.registerBeanDefition(loopBeanAName,loopBeanA);
        factory.registerBeanDefition(loopBeanBName,loopBeanB);
        factory.getBean(loopBeanAName);
    }
}
