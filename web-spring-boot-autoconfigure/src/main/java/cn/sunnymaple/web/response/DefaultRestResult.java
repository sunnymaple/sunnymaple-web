package cn.sunnymaple.web.response;

import cn.sunnymaple.web.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 同一格式的响应参数
 * @author wangzb
 * @date 2019/6/5 11:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultRestResult {
    /**
     * <p>错误码</p>
     * <p>阿里《java开发手册》第二章异常处理第（一）节第三条：
     * 【强制】全部正常，但不得不填充错误码时返回五个零：00000</p>
     */
    private String code = ErrorCode.OK;
    /**
     * <p>错误消息</p>
     * <p>错误码之外的业务独特信息由 message 来承载，而不是让错误码本身涵盖过多具体业务属性</p>
     * <p>请求成功时。默认为OK</p>
     */
    private String message = "OK";
    /**
     * <p>指纹识别，异常唯一识别ID，请求成功时为null</p>
     * <p>对接口的调用者没有实际的意义，该字段存在的意义在于发生错误（异常）时，在接口调用者不明确具体错误时，
     *    可以将此字段的值发送给接口开发者，由接口开发者来查找对应的错误信息</p>
     */
    private String fingerprint;
    /**
     * 响应结果对象，当请求成功时，响应结果对象封装在result中
     */
    private Object result;

    public DefaultRestResult(Object result) {
        this.result = result;
    }

    public DefaultRestResult(String code, String message,String fingerprint) {
        this.code = code;
        this.message = message;
        this.fingerprint = fingerprint;
    }
}
