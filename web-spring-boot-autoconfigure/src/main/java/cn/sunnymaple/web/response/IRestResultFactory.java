package cn.sunnymaple.web.response;


import cn.sunnymaple.web.error.Errors;

/**
 * RestResult工厂
 * @author wangzb
 * @param <S> 响应成功时返回的参数
 * @param <F> 响应失败时返回的参数
 */
public interface IRestResultFactory<S,F> {
    /**
     * 响应成功时创建结果集
     * @param result 响应结果对象
     * @param beforeBodyWriteParameter beforeBodyWrite方法对应的参数
     * @return
     */
    S success(Object result, BeforeBodyWriteParameter beforeBodyWriteParameter);

    /**
     * 响应失败时创建结果集
     * @param errors 错误响应体
     * @return
     */
    F failure(Errors errors);
}
