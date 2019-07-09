package ioc.test.method;

import factory.BeanDefinition;
import factory.BeanReference;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;
import ioc.bean.method.PeopleBean;

import java.util.ArrayList;
import java.util.List;

public class StaticFactoryMethodIocTest {

    public static void testDefaultStaticFactoryMethod(DefaultBeanFactory factory) throws Exception {
        String beanName = "peopleBean";

        BeanDefinition peopleBD = new GenericBeanDefinition();
        peopleBD.setBeanName(beanName);
        peopleBD.setBeanType("ioc.bean.method.PeopleBean");
        peopleBD.setFactoryMethodName("getPeopleBean");

        factory.registerBeanDefition(beanName,peopleBD);
        Object bean = factory.getBean(beanName);
        System.out.println(bean);
    }

    public static void testArgumentsStaticFactoryMethod(DefaultBeanFactory factory) throws Exception {
        String peopleBeanName = "peopleBean";
        String petBeanName = "petBean";

        BeanDefinition peopleBD = new GenericBeanDefinition();
        peopleBD.setBeanName(peopleBeanName);
        peopleBD.setBeanType("ioc.bean.method.PeopleBean");
        peopleBD.setFactoryMethodName("getPeopleBean");
        List<Object> constructorArgs = new ArrayList<>();
        constructorArgs.add("Terry");
        constructorArgs.add(0);
        constructorArgs.add(new BeanReference(petBeanName));
        peopleBD.setConstructorArgs(constructorArgs);

        BeanDefinition petBD = new GenericBeanDefinition();
        petBD.setBeanName(petBeanName);
        petBD.setBeanType("ioc.bean.method.PetBean");


        factory.registerBeanDefition(peopleBeanName,peopleBD);
        factory.registerBeanDefition(petBeanName,petBD);
        PeopleBean bean = (PeopleBean) factory.getBean(peopleBeanName);
        System.out.println(bean.toString());
    }
}
