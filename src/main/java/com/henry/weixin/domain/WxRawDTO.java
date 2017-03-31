package com.henry.weixin.domain;

import com.henry.weixin.common.enums.XmlType;

/**
 * Created by sanfen.yf on 2017/3/9.
 *
 * @author sanfen.yf
 * @date 2017/03/09
 */
public class WxRawDTO extends WxBaseDTO {
    private static final long serialVersionUID = -6542617204922893471L;

    @Override
    public XmlType getType() {
        return XmlType.RAW;
    }
}
