package cn.sunnymaple.web.error;

import cn.sunnymaple.web.error.me.Argument;
import lombok.Data;

import java.util.List;

/**
 * 异常错误信息
 * @author wangzb
 * @date 2020/4/14 11:24
 */
@Data
public class Error {
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常消息
     */
    private String message;

    /**
     * Exception arguments.
     */
    private List<Argument> arguments;
}
