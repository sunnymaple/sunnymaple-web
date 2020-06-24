package cn.sunnymaple.web.login.pattern.shiro.handler;

import cn.sunnymaple.web.error.me.adapter.attributes.IRefineUnknownExceptionContainer;
import cn.sunnymaple.web.login.exception.NotLoginException;
import org.apache.shiro.authz.UnauthorizedException;

import java.util.Map;


/**
 *  一个包含一些特殊异常的简单容器
 * @author wangzb
 * @date 2020/6/23 15:08
 */
public class ShiroRefineUnknownExceptionContainer implements IRefineUnknownExceptionContainer {
    /**
     * 给定一组经典的错误属性，它将从状态代码中确定要处理的异常
     *
     * @param attributes 表示错误属性的键-值对
     * @return 映射的异常
     */
    @Override
    public Exception refineUnknownException(Map<String, Object> attributes) {
        switch (getStatusCode(attributes)) {
            case 401:
                return new NotLoginException();
            case 403:
                return new UnauthorizedException();
            case 404:
                return new HandlerNotFoundException(getPath(attributes));
            default:
                return new IllegalStateException("The exception is null: " + attributes);
        }
    }
}
