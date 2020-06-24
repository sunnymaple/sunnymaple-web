package cn.sunnymaple.web.error.me.adapter.attributes;

import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;
import cn.sunnymaple.web.error.me.annotation.ExposeAsArg;
import cn.sunnymaple.web.error.me.handlers.ServletWebErrorHandler;

import java.util.Map;

import static cn.sunnymaple.web.error.ErrorCode.A0404;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * 一个包含一些特殊异常的简单容器
 * @author wangzb
 * @date 2020/6/23 15:00
 */
public interface IRefineUnknownExceptionContainer  {
    /**
     * 给定一组经典的错误属性，它将从状态代码中确定要处理的异常
     * @param attributes 表示错误属性的键-值对
     * @return 映射的异常
     */
    Exception refineUnknownException(Map<String, Object> attributes);

    /**
     * 获取请求路径
     * @param attributes
     * @return
     */
    default String getPath(Map<String, Object> attributes) {
        Object path = attributes.get("path");
        return path == null ? "unknown" : path.toString();
    }

    /**
     * Extracts the status code from error attributes.
     *
     * @param attributes The error attributes.
     * @return Extracted status code.
     */
    default int getStatusCode(Map<String, Object> attributes) {
        try {
            return (Integer) attributes.getOrDefault("status", 0);
        } catch (Exception e) {
            return 0;
        }
    }


    @ExceptionMapping(statusCode = NOT_FOUND, userTip = ServletWebErrorHandler.NO_HANDLER,errorCode = A0404)
    class HandlerNotFoundException extends RuntimeException {

        /**
         * The to-be-exposed path.
         */
        @ExposeAsArg(0)
        private final String path;

        public HandlerNotFoundException(String path) {
            this.path = path;
        }
    }
}
