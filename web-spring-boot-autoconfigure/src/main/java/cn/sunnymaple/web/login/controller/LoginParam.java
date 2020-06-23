package cn.sunnymaple.web.login.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 登录实体
 * @author wangzb
 * @date 2020/1/9 18:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginParam {
    /**
     * 登录用户名
     */
    @NotBlank(message = "login.userName_required")
    @ApiModelProperty(value = "登录账户，通常可以为用户名，手机号，邮箱等",required = true)
    private String userName;
    /**
     * 登录凭证密码
     */
    @NotBlank(message = "login.password_required")
    @ApiModelProperty(value = "密码，手机验证码登录时可以为验证码",required = true,notes = "")
    private String password;
    /**
     * 记住我
     */
    @ApiModelProperty(value = "记住我",allowableValues = "0,1")
    private Integer rememberMe;
    /**
     * 登录成功后的路径
     */
    @ApiModelProperty(value = "登录成功后转发的路径，只有VIEW模式下生效")
    private String loginSuccessPath;
}
