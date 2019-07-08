package ioc;

import factory.BeanDefinition;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;
import ioc.bean.AirBean;

public class IocTest {

    public static void main(String[] args) throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();

//        testDefaultConstructorBean(factory);
//        testDefaultStaticFactoryMethod(factory);
//        testDefaultFactoryBean(factory);
    }

//    private static void testArgumentConstructor(DefaultBeanFactory factory) throws Exception {
//        String beanName = "carBean";
//
//        BeanDefinition carBD = new GenericBeanDefinition();
//        carBD.setBeanName(beanName);
//        carBD.setBeanType("ioc.bean.CarBean");
//
//        factory.registerBeanDefition(beanName,carBD);
//        Object bean = factory.getBean(beanName);
//        System.out.println(bean);
//    }


    private static void testDefaultConstructor(DefaultBeanFactory factory) throws Exception {
        String beanName = "carBean";

        BeanDefinition carBD = new GenericBeanDefinition();
        carBD.setBeanName(beanName);
        carBD.setBeanType("ioc.bean.CarBean");

        factory.registerBeanDefition(beanName,carBD);
        Object bean = factory.getBean(beanName);
        System.out.println(bean);
    }

    private static void testDefaultStaticFactoryMethod(DefaultBeanFactory factory) throws Exception {
        String beanName = "peopleBean";

        BeanDefinition peopleBD = new GenericBeanDefinition();
        peopleBD.setBeanName(beanName);
        peopleBD.setBeanType("ioc.bean.PeopleBean");
        peopleBD.setFactoryMethodName("getPeopleBean");

        factory.registerBeanDefition(beanName,peopleBD);
        Object bean = factory.getBean(beanName);
        System.out.println(bean);
    }

    private static void testDefaultFactoryBean(DefaultBeanFactory factory) throws Exception {
        String airBeanName = "airBean";
        String airFactoryBeanName = "airBeanFactory";

        BeanDefinition airBD = new GenericBeanDefinition();
        airBD.setBeanName(airBeanName);
        airBD.setBeanType("ioc.bean.AirBean");
        airBD.setFactoryBeanName("airBeanFactory");
        airBD.setFactoryMethodName("createAirBean");

        BeanDefinition airFactoryBD = new GenericBeanDefinition();
        airFactoryBD.setBeanName("airBeanFactory");
        airFactoryBD.setBeanType("ioc.bean.AirBeanFactory");

        factory.registerBeanDefition(airBeanName,airBD);
        factory.registerBeanDefition(airFactoryBeanName,airFactoryBD);
        AirBean bean = (AirBean) factory.getBean(airBeanName);
        System.out.println(bean.getAqi());
    }
}
