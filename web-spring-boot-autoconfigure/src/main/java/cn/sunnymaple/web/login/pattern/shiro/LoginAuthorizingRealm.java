package cn.sunnymaple.web.login.pattern.shiro;

import cn.hutool.core.util.ObjectUtil;
import cn.sunnymaple.web.login.pattern.IPrincipalHandler;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户登录以及权限控制
 * @author wangzb
 * @date 2020/1/9 10:46
 */
@Component
public class LoginAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private List<IPermission> permissions;

    @Autowired
    private IPrincipalHandler principalHandler;

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //登录用户信息
        Serializable principal = principalHandler.getPrincipal();
        //判断缓存中是否有
        AuthorizationInfoCache authorizationInfos = AuthorizationInfoCache.getInstance();
        AuthorizationInfo authorizationInfo = authorizationInfos.get(principal);
        if (ObjectUtil.isNotNull(authorizationInfo)){
            return authorizationInfo;
        }
        Set<String> results = new HashSet<>(16);
        for (IPermission permission : permissions){
            Optional<Set<String>> setPermissionsOp = Optional.ofNullable(permission.getPermissions(principal));
            setPermissionsOp.ifPresent(setPermissions->{
                results.addAll(setPermissions);
            });
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(results);
        //添加到缓存
        authorizationInfos.put(principal,info);
        return info;
    }

    /**
     * 用户登录控制
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //2. 从 UsernamePasswordToken 中来获取 userName
        String userName = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        for (IPermission permission : permissions){
            LoginInfo loginInfo = permission.getPrincipal(userName, password);
            return new SimpleAuthenticationInfo(loginInfo.getPrincipal(),
                        loginInfo.getPassword(),
                        ByteSource.Util.bytes(loginInfo.getCredentialsSalt()), getName());
        }
        throw new AuthenticationException();
    }
}
