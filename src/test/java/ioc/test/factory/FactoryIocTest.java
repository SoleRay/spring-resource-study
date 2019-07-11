package ioc.test.factory;

import factory.BeanDefinition;
import factory.BeanReference;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;
import ioc.bean.factory.AirBean;

import java.util.ArrayList;
import java.util.List;

public class FactoryIocTest {

    public static void testDefaultFactoryBean(DefaultBeanFactory factory) throws Exception {
        String airBeanName = "airBean";
        String airFactoryBeanName = "airBeanFactory";

        BeanDefinition airBD = new GenericBeanDefinition();
        airBD.setBeanName(airBeanName);
        airBD.setFactoryBeanName("airBeanFactory");
        airBD.setFactoryMethodName("createAirBean");

        BeanDefinition airFactoryBD = new GenericBeanDefinition();
        airFactoryBD.setBeanName("airBeanFactory");
        airFactoryBD.setBeanType("ioc.bean.factory.AirBeanFactory");

        factory.registerBeanDefition(airBeanName,airBD);
        factory.registerBeanDefition(airFactoryBeanName,airFactoryBD);
        AirBean bean = (AirBean) factory.getBean(airBeanName);
        System.out.println(bean.toString());
    }

    public static void testArgumentsFactoryBean(DefaultBeanFactory factory) throws Exception {
        String airBeanName = "airBean";
        String cloudBeanName = "cloudBean";
        String airFactoryBeanName = "airBeanFactory";

        BeanDefinition airBD = new GenericBeanDefinition();
        airBD.setBeanName(airBeanName);
        airBD.setFactoryBeanName("airBeanFactory");
        airBD.setFactoryMethodName("createAirBean");
        List<Object> constructorArgs = new ArrayList<>();
        constructorArgs.add("100");
        constructorArgs.add(50);
        constructorArgs.add(new BeanReference(cloudBeanName));
        airBD.setConstructorArgs(constructorArgs);

        BeanDefinition cloudBD = new GenericBeanDefinition();
        cloudBD.setBeanName(cloudBeanName);
        cloudBD.setBeanType("ioc.bean.factory.CloudBean");

        BeanDefinition airFactoryBD = new GenericBeanDefinition();
        airFactoryBD.setBeanName("airBeanFactory");
        airFactoryBD.setBeanType("ioc.bean.factory.AirBeanFactory");

        factory.registerBeanDefition(airBeanName,airBD);
        factory.registerBeanDefition(airFactoryBeanName,airFactoryBD);
        factory.registerBeanDefition(cloudBeanName,cloudBD);
        AirBean bean = (AirBean) factory.getBean(airBeanName);
        System.out.println(bean);
    }
}
