package com.henry.weixin.robot.enums;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public enum ErrorCode {

    SUCCESS(100,"success"),
    SILENTCE(101,"Å¶"),
    INTER_ERROR(502,"ÏµÍ³´íÎó");

    int code;
    String msg;

    ErrorCode(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
