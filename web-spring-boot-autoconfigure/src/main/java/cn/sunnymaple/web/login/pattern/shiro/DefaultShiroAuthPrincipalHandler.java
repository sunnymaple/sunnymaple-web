package cn.sunnymaple.web.login.pattern.shiro;

import cn.sunnymaple.web.login.exception.NotLoginException;
import cn.sunnymaple.web.login.pattern.IPrincipalHandler;
import org.apache.shiro.SecurityUtils;

import java.io.Serializable;

/**
 * 定义默认的主体（一般为用户User实体对象，或者用户主键值）获取方式
 * @author wangzb
 * @date 2020/3/25 10:44
 */
public class DefaultShiroAuthPrincipalHandler implements IPrincipalHandler<Serializable> {
    /**
     * 获取当前登录的用户信息
     *
     * @return T addUser方法存储的用户信息
     * @throws NotLoginException 获取不到则抛出未登录或者登录超时异常，{@link NotLoginException}
     */
    @Override
    public Serializable getPrincipal() throws NotLoginException {
        return (Serializable) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 添加刚登录的用户信息
     *
     * @param user 用户实体，可以为用户主键（自增的Integer、唯一的UUID等），或者用户实体对象
     */
    @Override
    public void addPrincipal(Serializable user) {
        //由于使用shiro 不需要自行添加主体Principal信息，即用户的session，所以这是一个空方法
    }
}
