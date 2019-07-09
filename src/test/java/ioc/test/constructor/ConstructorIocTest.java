package ioc.test.constructor;

import factory.BeanDefinition;
import factory.BeanReference;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;

import java.util.ArrayList;
import java.util.List;

public class ConstructorIocTest {

    public static void testArgumentConstructor(DefaultBeanFactory factory) throws Exception {
        String carBeanName = "carBean";
        String wheelBeanName = "wheelBean";

        BeanDefinition carBD = new GenericBeanDefinition();
        carBD.setBeanName(carBeanName);
        carBD.setBeanType("ioc.bean.constructor.CarBean");
        List<Object> constructorArgs = new ArrayList<>();
        constructorArgs.add("Ferrari");
        constructorArgs.add(new BeanReference(wheelBeanName));
        carBD.setConstructorArgs(constructorArgs);

        GenericBeanDefinition wheelBD = new GenericBeanDefinition();
        wheelBD.setBeanName(wheelBeanName);
        wheelBD.setBeanType("ioc.bean.constructor.WheelBean");


        factory.registerBeanDefition(carBeanName,carBD);
        factory.registerBeanDefition(wheelBeanName,wheelBD);
        Object bean = factory.getBean(carBeanName);
        System.out.println(bean);
    }

    public static void testDefaultConstructor(DefaultBeanFactory factory) throws Exception {
        String beanName = "carBean";

        BeanDefinition carBD = new GenericBeanDefinition();
        carBD.setBeanName(beanName);
        carBD.setBeanType("ioc.bean.constructor.CarBean");

        factory.registerBeanDefition(beanName,carBD);
        Object bean = factory.getBean(beanName);
        System.out.println(bean);
    }
}
