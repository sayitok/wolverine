package com.henry.weixin.wxapi.sys;

import com.henry.weixin.common.annotation.ReqDesc;
import com.henry.weixin.common.utils.AccessTokenUtil;
import com.henry.weixin.common.utils.HttpUtil;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.wxapi.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/12.
 *
 * @author sanfen.yf
 * @date 2017/03/12
 */
@Component
public class SystemInfoService extends BaseService {


    private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";

    @ReqDesc(command = "sysip")
    public static String querySysIp(WxBaseDTO wxBaseDTO) {
        String token = AccessTokenUtil.getAccessToken();
        if(StringUtils.isBlank(token)) {
            logger.error("tokenªÒ»° ß∞‹£°");
            return null;
        }
        return HttpUtil.sendGet(String.format(API_URL, token));
    }
}
