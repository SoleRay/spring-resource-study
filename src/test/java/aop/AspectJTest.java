package aop;

import aop.bean.DuDu;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

public class AspectJTest {

    public static void main(String[] args) throws Exception{

//        String expression = "execution(* aop.bean.DuDu.eat*(..))";
        String expression = "execution(* aop.bean..*(..))";
        PointcutParser pp = PointcutParser
                .getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
        PointcutExpression pe = pp.parsePointcutExpression(expression);
        Method eat = DuDu.class.getDeclaredMethod("eat");
        ShadowMatch shadowMatch = pe.matchesMethodExecution(eat);
        System.out.println(shadowMatch.alwaysMatches());
    }
}
