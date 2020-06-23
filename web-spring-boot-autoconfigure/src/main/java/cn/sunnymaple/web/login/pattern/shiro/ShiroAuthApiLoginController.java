package cn.sunnymaple.web.login.pattern.shiro;

import cn.sunnymaple.web.login.controller.*;
import cn.sunnymaple.web.login.exception.NotLoginException;
import cn.sunnymaple.web.login.interceptor.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 使用shiro验证登录和权限控制时的默认登录接口
 *  web模式为{@link WebPattern#API}
 * @author wangzb
 * @date 2020/3/24 13:02
 */
@RestController
@RequestMapping("/")
@Web(WebPattern.API)
@Validated
@Api(value = "登录模块，只关注登录退出接口，其他接口请忽略",tags = {"登录模块，只关注登录退出接口，其他接口请忽略"})
public class ShiroAuthApiLoginController implements ILoginApiController<Serializable> {

    /**
     * 登录接口
     * 注意：实现该接口需要使用@RessponseBody或者类中使用@RestController注解
     * produces = "application/json"
     *
     * @param LoginParam 登录参数 {@link LoginParam}
     * @return 登录成功后获取的用户信息
     */
    @Override
    @PostMapping(value = "${login.login-url:" + LoginConstant.DEFAULT_LOGIN_URL + "}",produces = "application/json")
    @Login
    @ApiOperation(value = "登录接口", notes = "用户登录")
    public Serializable loginApi(@Valid @RequestBody LoginParam LoginParam) {
        //是否记住我
        Integer rememberMeInt = LoginParam.getRememberMe();
        boolean rememberMe = (rememberMeInt!=null && rememberMeInt == 1) ? true : false;
        ShiroUtils.login(LoginParam.getUserName(),LoginParam.getPassword(),rememberMe);
        Subject currentUser = SecurityUtils.getSubject();
        Serializable principal = (Serializable) currentUser.getPrincipal();
        return principal;
    }

    /**
     * 被登录拦截时调用的接口，建议抛出异常{@link NotLoginException}
     *
     * @throws NotLoginException
     */
    @Override
    @RequestMapping(value = "${login.not-login-url:" + LoginConstant.DEFAULT_NOT_LOGIN_URL + "}",produces = "application/json")
    public void notLoginApi() throws NotLoginException {
        throw new NotLoginException();
    }

    /**
     * 退出登录接口，一般作删除session或者token操作
     * 注意：实现该接口需要使用@RessponseBody或者类中使用@RestController注解
     * produces = "application/json"
     *
     * @return 一般为退出成功与否登录的提示消息
     */
    @Override
    @GetMapping(value = "${login.logout-url:" + LoginConstant.DEFAULT_LOGOUT_URL + "}",produces = "application/json")
    @ApiOperation(value = "退出登录，注意使用该接口无效，实际退出登录接口为" + ShiroUtils.DEFAULT_EXIT_URL, notes = "用户登录")
    public String logoutApi() {
        return "退出成功！";
    }
}
