package cn.sunnymaple.web.error;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.error.me.HttpError;
import cn.sunnymaple.web.error.me.WebErrorHandlerPostProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

/**
 * 日志打印处理器
 * @author wangzb
 * @date 2020/3/26 20:32
 */
@Slf4j
public class LoggingErrorWebErrorHandlerPostProcessor implements WebErrorHandlerPostProcessor {
    /**
     * The logic to execute when we finished to handle the exception and just before returning the
     * result.
     *
     * @param error HttpError to act upon.
     */
    @Override
    public void process(HttpError error) {
        if (error.getHttpStatus().is4xxClientError()) {
            log.warn("[{}] {} ,{}", getFingerprint(error), prepareMessage(error),getMessage(error.getOriginalException()));
        } else if (error.getHttpStatus().is5xxServerError()) {
            log.error("[{}] {}", getFingerprint(error), prepareMessage(error), error.getOriginalException());
        }
    }

    /**
     * 获取异常信息
     * @param throwable 异常{@link Throwable}
     * @return
     */
    public String getMessage(Throwable throwable){
        return StrUtil.blankToDefault(throwable.getMessage(),"");
    }

    /**
     * 获取手指Fingerprint
     * @param error HttpError to act upon.
     * @return
     */
    private String getFingerprint(HttpError error){
        return StrUtil.blankToDefault(error.getFingerprint(),"");
    }

    /**
     * 解析错误信息
     * @param error HttpError to act upon.
     * @return
     */
    private String prepareMessage(HttpError error) {
        return error.getErrors().stream()
                .map(HttpError.CodedMessage::getMessage)
                .collect(Collectors.joining("; "));
    }
}
