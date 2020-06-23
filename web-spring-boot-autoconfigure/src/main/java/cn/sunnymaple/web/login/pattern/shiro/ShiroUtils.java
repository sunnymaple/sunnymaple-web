package cn.sunnymaple.web.login.pattern.shiro;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * Shiro工具类
 * @author wangzb
 * @date 2018-11-01 12:36
 */
public class ShiroUtils {

    /**
     * 默认的加密次数
     */
    public static final int HASH_INTERAYION = 1024;

    /**
     * 默认的加密方式
     */
    public static final String HASH_ALGORITHM_NAME = "MD5";

    /**
     * shiro退出登录的接口（url）
     */
    public static final String DEFAULT_EXIT_URL = "/logout";
    /**
     * errorCode:用户名密码错误
     */
    public static final String DEFAULT_AUTH_ERROR_CODE = "login.pwdUserName_error";

    /**
     * 获取加密后的密码
     * @param algorithmName 加密方式
     * @param iterations 累加加密次数
     * @param password 密码
     * @param salt 盐值
     * @return
     */
    public static String encryption(String algorithmName, Integer iterations,
                             String password,String salt) {
        if (StrUtil.isBlank(algorithmName)){
            algorithmName = HASH_ALGORITHM_NAME;
        }
        if (iterations == null){
            iterations = HASH_INTERAYION;
        }
        if (StrUtil.isBlank(password)){
            return "";
        }
        Object saltBate = ByteSource.Util.bytes(salt);
        Object result = new SimpleHash(algorithmName, password, saltBate, iterations);
        return result.toString();
    }

    /**
     * 加密(默认使用userName作为盐值)
     * @param password 密码
     * @param salt 颜值，通常为用户主键
     * @return
     */
    public static String encryption(String password,String salt){
        ShiroAuthProperties shiroAuthProperties = ShiroAuthProperties.getInstance();
        return encryption(shiroAuthProperties.getHashAlgorithmName(),
                shiroAuthProperties.getHashIterations(),password,salt);
    }


    /**
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberMe 记住我
     * @throws Exception
     */
    public static void login(String userName,String password,boolean rememberMe){
        //获取当前登录对象
        Subject currentUser = SecurityUtils.getSubject();
        //判断是否认证（登录）
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(rememberMe);
            //执行登录
            currentUser.login(token);
        }
    }
}
