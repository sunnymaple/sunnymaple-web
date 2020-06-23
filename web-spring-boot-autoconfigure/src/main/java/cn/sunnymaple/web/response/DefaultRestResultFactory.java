package cn.sunnymaple.web.response;

import cn.sunnymaple.web.error.Error;
import cn.sunnymaple.web.error.Errors;
/**
 * 默认的RestResult工厂类
 * @author wangzb
 * @date 2019/6/18 10:29
 */
public class DefaultRestResultFactory implements ISameRestResultFactory<DefaultRestResult>{
    /**
     * 响应成功时创建结果集
     *
     * @param result                   响应结果对象
     * @param beforeBodyWriteParameter beforeBodyWrite方法对应的参数
     * @throws Exception
     * @return
     */
    @Override
    public DefaultRestResult success(Object result, BeforeBodyWriteParameter beforeBodyWriteParameter) {
        if (result instanceof DefaultRestResult){
            return (DefaultRestResult) result;
        }
        return new DefaultRestResult(result);
    }

    /**
     * 响应失败时创建结果集
     *
     * @param errors 错误响应体
     * @return
     */
    @Override
    public DefaultRestResult failure(Errors errors) {
        Error error = errors.getErrors()
                .stream()
                .findFirst()
                .orElse(new Error());
        return new DefaultRestResult(error.getCode(),error.getMessage(),errors.getFingerprint());
    }
}
