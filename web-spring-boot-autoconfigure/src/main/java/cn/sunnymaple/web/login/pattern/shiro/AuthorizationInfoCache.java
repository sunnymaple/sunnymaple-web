package cn.sunnymaple.web.login.pattern.shiro;

import cn.hutool.cache.impl.FIFOCache;
import org.apache.shiro.authz.AuthorizationInfo;

import java.io.Serializable;

/**
 * 用户权限信息缓存
 * @author wangzb
 * @date 2020/3/24 18:39
 */
class AuthorizationInfoCache extends FIFOCache<Serializable, AuthorizationInfo> {
    /**
     * 默认10个
     */
    private static final Integer DEFAULT_CAPACITY = 10;

    private static final Long DEFAULT_TIMEOUT = 5 * 1000 * 60L;

    /**
     * 构造，默认对象不过期
     *
     * @param capacity 容量
     */
    private AuthorizationInfoCache(int capacity,long timeout) {
        super(capacity,timeout);
    }

    private static volatile AuthorizationInfoCache authorizationInfo;

    /**
     * 获取实例
     * @param capacity
     * @param timeout
     * @return
     */
    public static AuthorizationInfoCache getInstance(Integer capacity,Long timeout){
        if (authorizationInfo == null){
            synchronized (AuthorizationInfoCache.class){
                if (authorizationInfo == null){
                    if (capacity == null){
                        capacity = DEFAULT_CAPACITY;
                    }
                    if (timeout == null){
                        timeout = DEFAULT_TIMEOUT;
                    }
                    authorizationInfo = new AuthorizationInfoCache(capacity,timeout);
                }
            }
        }
        return authorizationInfo;
    }

    /**
     * 获取实例
     * @return
     */
    public static AuthorizationInfoCache getInstance(){
        return getInstance(DEFAULT_CAPACITY,DEFAULT_TIMEOUT);
    }

}
