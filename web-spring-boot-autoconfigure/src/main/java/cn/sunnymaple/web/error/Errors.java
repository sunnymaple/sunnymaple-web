package cn.sunnymaple.web.error;

import lombok.Data;

import java.util.List;

/**
 * 错误体
 * @author wangzb
 * @date 2020/4/14 11:23
 */
@Data
public class Errors {
    /**
     * 指纹识别
     */
    private String fingerprint;
    /**
     * 异常集合
     */
    private List<Error> errors;
}
