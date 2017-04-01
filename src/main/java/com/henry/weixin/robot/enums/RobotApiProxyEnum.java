package com.henry.weixin.robot.enums;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public enum RobotApiProxyEnum {
    SIMSIMI(1),
    DOUQQ(2);
    int code;
    RobotApiProxyEnum(int c) {
        this.code = c;
    }

    public int getCode() {
        return code;
    }

    public static RobotApiProxyEnum getProxy(int code) {
        for(RobotApiProxyEnum proxyEnum:values()) {
            if(code==proxyEnum.getCode()) {
                return proxyEnum;
            }
        }
        return null;
    }
}
