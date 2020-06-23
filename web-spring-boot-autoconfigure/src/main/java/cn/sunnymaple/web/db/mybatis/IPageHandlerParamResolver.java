package cn.sunnymaple.web.db.mybatis;

import org.aspectj.lang.JoinPoint;

/**
 * 分页参数解析器
 * @author wangzb
 * @date 2019/12/11 20:14
 */
public interface IPageHandlerParamResolver {

    /**
     * 是否支持该解析器
     * @param expression spEl表达式
     * @return
     */
    boolean supports(String expression);

    /**
     * 解析获取参数值
     * @param point JoinPoint
     * @param expression spEl表达式
     * @return
     */
    Integer resolver(JoinPoint point, String expression);
}
