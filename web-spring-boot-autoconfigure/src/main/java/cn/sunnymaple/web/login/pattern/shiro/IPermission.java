package cn.sunnymaple.web.login.pattern.shiro;

import org.springframework.core.Ordered;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户认证，用户获取用户权限以及密码
 * @author wangzb
 * @date 2020/1/9 10:50
 */
public interface IPermission extends Ordered {
    /**
     * 获取权限
     * @param principal 用户主体信息，可以为Integer类型的自增主键、唯一的UUID或者用户实体对象（必须实现{@link Serializable}）
     * @return
     */
    default Set<String> getPermissions(Serializable principal){
        return null;
    }

    /**
     * 获取用户主体信息
     * @param userName 用户名，通常是具体登录使用的用户名（手机号或者邮箱），具有唯一性
     * @param password 用户提交的密码（未经过shiro加密器加密的文明密码）
     * @return
     */
    LoginInfo getPrincipal(String userName, String password);

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
