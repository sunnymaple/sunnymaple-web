package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.NumberUtil;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 参数index解析#p0
 * @author wangzb
 * @date 2019/12/11 22:04
 */
@Component
public class ParamIndexPageHandlerParamResolver extends AbstractSpElPageHandlerParamResolver{

    /**
     * 获取上小文
     *
     * @param rootObject 当前类对象
     * @param method     方法
     * @param args       方法参数值
     * @return
     */
    @Override
    public EvaluationContext getEvaluationContext(Object rootObject, Method method, Object[] args) {
        return new MethodBasedEvaluationContext(rootObject,method,args,parameterNameDiscoverer);
    }

    /**
     * 是否支持该解析器
     *
     * @param expression spEl表达式
     * @return
     */
    @Override
    public boolean supports(String expression) {
        return (expression.startsWith(PageHandlerConstant.PARAM_NAME_START_WITH_A)
                || expression.startsWith(PageHandlerConstant.PARAM_NAME_START_WITH_P))
                && NumberUtil.isInteger(expression.substring(2));
    }
}
