package com.henry.weixin.common.enums;

import com.henry.weixin.dao.BaseDAO;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
public enum BizType {
    DEFAULT(0,"默认业务"),
    PF_PACK(1,"浦发红包");

    int code;
    String desc;

    BizType(int c,String s){
        this.code = c;
        this.desc = s;
    }

    public static BizType getBizType(int code) {
        for(BizType bizType:values()) {
            if(bizType.code==code) {
                return bizType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
