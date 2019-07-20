package aop.advisor;

import aop.pointcut.Pointcut;

public interface PointcutAdvisor extends Advisor{

    Pointcut getPointcut();
}
