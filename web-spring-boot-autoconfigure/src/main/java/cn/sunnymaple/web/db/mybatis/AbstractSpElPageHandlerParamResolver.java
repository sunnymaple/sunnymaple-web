package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

/**
 * spEl参数解析器
 * @author wangzb
 * @date 2019/12/11 21:32
 */
public abstract class AbstractSpElPageHandlerParamResolver implements IPageHandlerParamResolver {

    /**
     * SPEL表达式解析器
     */
    protected ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 获取方法参数名称发现器
     */
    protected ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 获取上小文
     * @param rootObject 当前类对象
     * @param method 方法
     * @param args 方法参数值
     * @return
     */
    abstract EvaluationContext getEvaluationContext(Object rootObject, Method method, Object[] args);

    /**
     * 解析获取参数值
     *
     * @param point      JoinPoint
     * @param expression spEl表达式
     * @return
     */
    @Override
    public Integer resolver(JoinPoint point, String expression) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        // 获取传入参数值
        Object[] args = point.getArgs();
        if (ArrayUtil.isEmpty(args)){
            throw new SpElParamNotFoundException();
        }
        // 获取参数名
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        // 将参数名与参数值放入参数上下文
        EvaluationContext evaluationContext = getEvaluationContext(point.getTarget(),method,args);
        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }
        // 计算表达式(根据参数上下文)
        Expression expression1 = expressionParser.parseExpression(expression);
        Class<?> valueType = expression1.getValueType(evaluationContext);
        Object value = expression1.getValue(evaluationContext, valueType);
        if (value!=null){
            return Integer.parseInt(value.toString());
        }
        return null;
    }
}
