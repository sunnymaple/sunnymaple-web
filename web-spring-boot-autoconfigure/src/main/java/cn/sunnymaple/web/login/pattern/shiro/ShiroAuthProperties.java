package cn.sunnymaple.web.login.pattern.shiro;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * shiro配置
 * @author wangzb
 * @date 2020/3/24 14:41
 */
@Data
@ConfigurationProperties(prefix = "login.shiro")
public class ShiroAuthProperties implements ApplicationContextAware {

    private static ShiroAuthProperties shiroAuthProperties;

    /**
     * 加密模式，默认使用MD5
     */
    private String hashAlgorithmName = ShiroUtils.HASH_ALGORITHM_NAME;
    /**
     * 加密次数,默认为1024次
     * 别问我为什么默认使用1024，是程序员都懂
     */
    private Integer hashIterations = ShiroUtils.HASH_INTERAYION;

    /**
     * 缓存个数
     * 用于用户权限信息缓存
     */
    private Integer cacheCapacity;
    /**
     * 缓存超时时间
     * 用于用户权限信息缓存
     */
    private Long cacheTimeout;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.shiroAuthProperties = applicationContext.getBean(ShiroAuthProperties.class);
    }

    public static ShiroAuthProperties getInstance(){
        return shiroAuthProperties;
    }
}
