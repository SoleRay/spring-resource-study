package ioc.test.di;

import factory.*;

import java.util.ArrayList;

public class DenpendencyInjectIocTest {

    public static void testDI(DefaultBeanFactory factory) throws Exception {
        String houseBeanName = "houseBean";
        String doorBeanName = "doorBean";

        BeanDefinition houseBD = new GenericBeanDefinition();
        houseBD.setBeanName(houseBeanName);
        houseBD.setBeanType("ioc.bean.di.HouseBean");
        ArrayList<PropertyValue> pvs = new ArrayList<>();
        PropertyValue highPv = new PropertyValue("high", 100);
        PropertyValue lowPv = new PropertyValue("low", 50);
        PropertyValue doorBeanPv = new PropertyValue("doorBean", new BeanReference(doorBeanName));
        pvs.add(highPv);
        pvs.add(lowPv);
        pvs.add(doorBeanPv);
        houseBD.setPropertyValues(pvs);


        GenericBeanDefinition doorBD = new GenericBeanDefinition();
        doorBD.setBeanName(doorBeanName);
        doorBD.setBeanType("ioc.bean.di.DoorBean");

        factory.registerBeanDefition(houseBeanName,houseBD);
        factory.registerBeanDefition(doorBeanName,doorBD);
        Object bean = factory.getBean(houseBeanName);
        System.out.println(bean);
    }
}
