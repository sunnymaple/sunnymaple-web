package cn.sunnymaple.web.login.pattern;

import cn.sunnymaple.web.login.exception.NotLoginException;

import java.io.Serializable;

/**
 * 登录
 * @author wangzb
 * @date 2020/3/25 10:29
 */
public interface IPrincipalHandler <T extends Serializable>{

    /**
     * 获取当前登录的用户信息
     * @return T addUser方法存储的用户信息
     * @throws NotLoginException 获取不到则抛出未登录或者登录超时异常，{@link NotLoginException}
     */
    T getPrincipal() throws NotLoginException;

    /**
     * 添加刚登录的用户信息
     * @param user 用户实体，可以为用户主键（自增的Integer、唯一的UUID等），或者用户实体对象
     */
    void addPrincipal(T user);
}
