package cn.sunnymaple.web.response;

import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/**
 * beforeBodyWrite方法对应的参数
 * @author wangzb
 * @date 2019/6/18 19:18
 */
@Data
public class BeforeBodyWriteParameter {

    private MethodParameter methodParameter;

    private MediaType mediaType;

    private Class aClass;

    private ServerHttpRequest serverHttpRequest;

    private ServerHttpResponse serverHttpResponse;

    public BeforeBodyWriteParameter() {
    }

    public BeforeBodyWriteParameter(MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        this.methodParameter = methodParameter;
        this.mediaType = mediaType;
        this.aClass = aClass;
        this.serverHttpRequest = serverHttpRequest;
        this.serverHttpResponse = serverHttpResponse;
    }
}
