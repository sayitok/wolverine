package com.henry.weixin.robot.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public enum MethodEnum {
    MEMBER("ls"),
    MSG("msg");

    String m;

    MethodEnum(String s) {
        m = s;
    }

    public String getM() {
        return m;
    }

    public static MethodEnum getMethod(String m) {
        if(StringUtils.isBlank(m)) {
            return null;
        }
        for(MethodEnum methodEnum:values()) {
            if(methodEnum.m.equals(m)) {
                return methodEnum;
            }
        }
        return null;
    }
}
