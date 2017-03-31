package com.henry.weixin.common.enums;

import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxImageDTO;
import com.henry.weixin.domain.WxLinkDTO;
import com.henry.weixin.domain.WxRawDTO;
import com.henry.weixin.domain.WxTextDTO;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public enum XmlType {
    TEXT("text", WxTextDTO.class),
    LINK("link", WxLinkDTO.class),
    IMAGE("image", WxImageDTO.class),
    RAW("raw", WxRawDTO.class);

    String type;

    Class<? extends WxBaseDTO> clazz;

    XmlType(String type,Class<? extends WxBaseDTO> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static XmlType getReqType(String t) {
        for (XmlType val:values()) {
            if(val.type.equals(t)) {
                return val;
            }
        }
        return null;
    }

    public Class<? extends WxBaseDTO> getClazz() {
        return clazz;
    }

    public String getType() {
        return type;
    }
}
