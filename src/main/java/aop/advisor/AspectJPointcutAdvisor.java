package aop.advisor;

import aop.pointcut.AspectJExpressionPointcut;
import aop.pointcut.Pointcut;

public class AspectJPointcutAdvisor implements PointcutAdvisor {

    private String expression;

    private String adviceBeanName;

    private AspectJExpressionPointcut pointcut;

    public AspectJPointcutAdvisor(String expression, String adviceBeanName) {
        this.expression = expression;
        this.adviceBeanName = adviceBeanName;
        pointcut = new AspectJExpressionPointcut(expression);

    }

    @Override
    public String getAdviceBeanName() {
        return adviceBeanName;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
