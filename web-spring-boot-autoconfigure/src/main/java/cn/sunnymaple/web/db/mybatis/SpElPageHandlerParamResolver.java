package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.NumberUtil;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 带#号的，解析SpEl表达式
 * @author wangzb
 * @date 2019/12/11 20:58
 */
@Component
public class SpElPageHandlerParamResolver extends AbstractSpElPageHandlerParamResolver{

    /**
     * 是否支持该解析器
     *
     * @param expression spEl表达式
     * @return
     */
    @Override
    public boolean supports(String expression) {
        return expression.startsWith(PageHandlerConstant.PARAM_NAME_START_WITH)
                && !NumberUtil.isInteger(expression.substring(2));
    }
    /**
     * 获取上小文
     * @param rootObject 当前类对象
     * @param method 方法
     * @param args 方法参数值
     * @return
     */
    @Override
    public EvaluationContext getEvaluationContext(Object rootObject, Method method, Object[] args) {
        return new StandardEvaluationContext();
    }
}
