package cn.sunnymaple.web.login.controller;

import cn.sunnymaple.web.login.exception.NotLoginException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 定义登录接口的Controller规范，针对有API接口，如移动端API接口项目或者提供第三方使用的API接口
 * @author wangzb
 * @date 2020/3/23 18:13
 */
public interface ILoginApiController<T extends Serializable> extends ILoginController{

    /**
     * 登录接口
     * 注意：实现该接口需要使用@RessponseBody或者类中使用@RestController注解
     * produces = "application/json"
     * @param LoginParam 登录参数 {@link LoginParam}
     * @return 登录成功后获取的用户信息
     */
    T loginApi(@Valid @RequestBody LoginParam LoginParam);

    /**
     * 被登录拦截时调用的接口，建议抛出异常{@link NotLoginException}
     * @throws NotLoginException
     */
    void notLoginApi() throws NotLoginException;

    /**
     * 退出登录接口，一般作删除session或者token操作
     * 注意：实现该接口需要使用@RessponseBody或者类中使用@RestController注解
     * produces = "application/json"
     * @return 一般为退出成功与否登录的提示消息
     */
    String logoutApi();
}
