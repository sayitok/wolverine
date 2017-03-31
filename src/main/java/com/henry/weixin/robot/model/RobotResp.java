package com.henry.weixin.robot.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public class RobotResp implements Serializable {
    private static final long serialVersionUID = -8376582567311318022L;

    private int code;

    private String content;

    private String msg;

    public RobotResp() {

    }

    public RobotResp(int code,String msg,String content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
