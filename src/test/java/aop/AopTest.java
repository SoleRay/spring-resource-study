package aop;

import aop.advisor.AspectJPointcutAdvisor;
import aop.advisor.PointcutAdvisor;
import aop.bean.AdvisorAutoProxyCreator;
import aop.bean.DuDu;
import factory.BeanDefinition;
import factory.DefaultBeanFactory;
import factory.GenericBeanDefinition;

public class AopTest {

    public static void main(String[] args) throws Exception {
        String expression = "execution(* aop.bean..*(..))";
        DefaultBeanFactory factory = new DefaultBeanFactory();

        AdvisorAutoProxyCreator bpp = getAdvisorAutoProxyCreator(expression);

        factory.registerBeanPostProcessor(bpp);

        registerAdvice(factory);

        String duduBeanName = "dudu";
        BeanDefinition duduBD = new GenericBeanDefinition();
        duduBD.setBeanName(duduBeanName);
        duduBD.setBeanType("aop.bean.DuDu");

        factory.registerBeanDefition(duduBeanName,duduBD);

        DuDu dudu = (DuDu) factory.getBean(duduBeanName);
//        dudu.eat();
        System.out.println("Finally.."+dudu.walk());

    }

    private static void registerAdvice(DefaultBeanFactory factory) throws Exception {
        String beforeAdviceBeanName = "beforeAdvice";
        BeanDefinition beforeAdvice = new GenericBeanDefinition();
        beforeAdvice.setBeanName(beforeAdviceBeanName);
        beforeAdvice.setBeanType("aop.advice.BeforeAdvice");

        String aroundAdviceBeanName = "aroundAdvice";
        BeanDefinition aroundAdvice = new GenericBeanDefinition();
        aroundAdvice.setBeanName(aroundAdviceBeanName);
        aroundAdvice.setBeanType("aop.advice.AroundAdvice");

        String afterAdviceBeanName = "afterAdvice";
        BeanDefinition afterAdvice = new GenericBeanDefinition();
        afterAdvice.setBeanName(afterAdviceBeanName);
        afterAdvice.setBeanType("aop.advice.AfterAdvice");

        factory.registerBeanDefition(beforeAdviceBeanName,beforeAdvice);
        factory.registerBeanDefition(aroundAdviceBeanName,aroundAdvice);
        factory.registerBeanDefition(afterAdviceBeanName,afterAdvice);

    }

    private static AdvisorAutoProxyCreator getAdvisorAutoProxyCreator(String expression) {
        AdvisorAutoProxyCreator bpp = new AdvisorAutoProxyCreator();
        PointcutAdvisor beforeAdvisor = new AspectJPointcutAdvisor(expression,"beforeAdvice");
        PointcutAdvisor afterAdvisor = new AspectJPointcutAdvisor(expression,"afterAdvice");
        PointcutAdvisor aroundAdvisor = new AspectJPointcutAdvisor(expression,"aroundAdvice");


        bpp.registerAdvisor(beforeAdvisor);
        bpp.registerAdvisor(afterAdvisor);
        bpp.registerAdvisor(aroundAdvisor);
        return bpp;
    }
}
