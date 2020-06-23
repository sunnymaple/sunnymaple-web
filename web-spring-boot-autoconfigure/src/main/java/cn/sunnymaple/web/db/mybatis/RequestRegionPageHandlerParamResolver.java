package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 从request域中获取参数值
 * @author wangzb
 * @date 2019/12/11 20:21
 */
@Component
public class RequestRegionPageHandlerParamResolver implements IPageHandlerParamResolver {

    @Autowired
    private HttpServletRequest request;

    /**
     * 是否支持该解析器
     *
     * @param expression spEl表达式
     * @return
     */
    @Override
    public boolean supports(String expression) {
        return !expression.startsWith(PageHandlerConstant.PARAM_NAME_START_WITH);
    }

    /**
     * 解析获取参数值
     *
     * @param point      JoinPoint
     * @param expression spEl表达式
     * @return
     */
    @Override
    public Integer resolver(JoinPoint point, String expression) {

        String value = request.getParameter(expression);

        boolean isInt = NumberUtil.isInteger(value);

        if (!StrUtil.isEmpty(value) && isInt && Integer.parseInt(value)>0){
            return Integer.parseInt(value);
        }
        return null;
    }
}
