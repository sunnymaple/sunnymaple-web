package cn.sunnymaple.web.db.mybatis;


import cn.sunnymaple.web.error.ErrorCode;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;
import cn.sunnymaple.web.error.me.handlers.LastResortWebErrorHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 请求接口参数缺省
 * 程序员操作错误，指定分页从接口方法的参数中获取，但实际接口中无任何参数
 * @author wangzb
 * @date 2019/12/11 21:10
 */
@ExceptionMapping(errorCode= ErrorCode.B0001,statusCode= INTERNAL_SERVER_ERROR,userTip = LastResortWebErrorHandler.UNKNOWN_ERROR_CODE)
public class SpElParamNotFoundException extends RuntimeException{

}
