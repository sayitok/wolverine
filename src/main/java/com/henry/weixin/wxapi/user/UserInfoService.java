package com.henry.weixin.wxapi.user;

import java.text.MessageFormat;

import com.henry.weixin.common.annotation.ReqDesc;
import com.henry.weixin.common.constant.SysConfig;
import com.henry.weixin.common.utils.AccessTokenUtil;
import com.henry.weixin.common.utils.HttpUtil;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.wxapi.BaseApi;
import com.henry.weixin.wxapi.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/11.
 *
 * @author sanfen.yf
 * @date 2017/03/11
 */
@Component
public class UserInfoService extends BaseService {



    private static final String USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    @ReqDesc(command = "myinfo", alias = {"Œ“"})
    public static String queryUserByOpenId(WxBaseDTO wxBaseDTO) {
        if(wxBaseDTO==null) {
            return null;
        }
        String token = AccessTokenUtil.getAccessToken();
        if(StringUtils.isBlank(token)) {
            logger.error("tokenªÒ»° ß∞‹£°");
            return null;
        }
        return HttpUtil.sendGet(String.format(USER_URL, token,wxBaseDTO.getFromUserName()));
    }
}
