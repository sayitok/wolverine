package com.henry.weixin.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.common.utils.HttpUtil;
import com.henry.weixin.common.utils.RespUtil;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxReqDTO;
import com.henry.weixin.domain.WxTextDTO;
import com.henry.weixin.wxapi.BaseApi;
import com.henry.weixin.wxapi.user.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
@Component
public class DefaultMsgHandler extends XmlMsgHandler {


    @Override
    protected String execute(WxReqDTO wxReqDTO, HttpServletResponse response) throws IOException {
        WxBaseDTO wxBaseDTO = wxReqDTO.getWxXmlDO();
        if(wxBaseDTO instanceof WxTextDTO) {
            String ret = executeShell((WxTextDTO)wxBaseDTO);
            if(StringUtils.isNotBlank(ret)){
                return RespUtil.createTextResp(wxBaseDTO.getFromUserName(),ret);
            }
        }
        return super.createDefaultResp(wxReqDTO);
    }

    @Override
    public boolean recognize(WxReqDTO wxReqDTO) {
        return true;
    }


    private String executeShell(WxTextDTO wxTextDTO) {
        String content = wxTextDTO.getContent().trim();
        return BaseApi.execute(content,wxTextDTO);
    }
}
