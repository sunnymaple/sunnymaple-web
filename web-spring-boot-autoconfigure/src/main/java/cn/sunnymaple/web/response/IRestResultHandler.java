package cn.sunnymaple.web.response;

import org.springframework.core.Ordered;

/**
 * Rest参数处理
 * @author wangzb
 * @date 2020/4/16 11:09
 */
public interface IRestResultHandler extends Ordered {
    /**
     * 是否可以处理，返回true，则执行handle方法
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    boolean canHandle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter);

    /**
     * 处理响应结果对象
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    Object handle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter);

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    default int getOrder() {
        //默认最低优先权
        return Ordered.LOWEST_PRECEDENCE;
    }
}
