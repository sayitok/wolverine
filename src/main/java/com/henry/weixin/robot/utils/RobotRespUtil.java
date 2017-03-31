package com.henry.weixin.robot.utils;

import com.henry.weixin.robot.enums.ErrorCode;
import com.henry.weixin.robot.model.RobotResp;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public class RobotRespUtil {

    private RobotRespUtil() {}

    public static RobotResp createResp(int code, String msg,String content) {
        return new RobotResp(code,msg,content);
    }

    public static RobotResp createSuccessResp() {
        return createErrorResp(ErrorCode.SUCCESS,null,null);
    }

    public static RobotResp createErrorResp(ErrorCode errorCode,String msg, String content) {
        if(errorCode==null) {
            errorCode = ErrorCode.INTER_ERROR;
        }
        int code = errorCode.getCode();
        if(StringUtils.isBlank(msg)) {
            msg = errorCode.getMsg();
        }
        return new RobotResp(code,msg,content);
    }

    public static RobotResp createErrorResp(String msg) {
        ErrorCode errorCode = ErrorCode.INTER_ERROR;
        int code = errorCode.getCode();
        if(StringUtils.isBlank(msg)) {
            msg = errorCode.getMsg();
        }
        return new RobotResp(code,msg,null);
    }
}
