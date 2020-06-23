package cn.sunnymaple.web.login.pattern.shiro;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录用户信息
 * @author wangzb
 * @date 2020/1/9 16:07
 */
@Data
@Builder
@Accessors(chain = true)
public class LoginInfo {
    /**
     * 用户实体
     * 通常为用户实体对象或者用于登录的用户名
     */
    private Serializable principal;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 盐值
     * 通常为主键
     */
    private Object credentialsSalt;
}
