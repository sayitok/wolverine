package com.henry.weixin.common.enums;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public enum HttpMethod {
    GET(1),
    POST(2);

    int code;

    HttpMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
