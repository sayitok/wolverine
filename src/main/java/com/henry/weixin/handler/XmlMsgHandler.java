package com.henry.weixin.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.common.utils.RespUtil;
import com.henry.weixin.dao.WeixinMsgDao;
import com.henry.weixin.domain.QueryStrDO;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxImageDTO;
import com.henry.weixin.domain.WxReqDTO;
import com.henry.weixin.domain.WxTextDTO;
import com.henry.weixin.domain.db.WeixinMsgDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public abstract class XmlMsgHandler implements MsgHandler {

    protected final static Logger logger = LoggerFactory.getLogger(XmlMsgHandler.class);
    @Autowired
    private WeixinMsgDao weixinMsgDao;

    @Override
    public void process(WxReqDTO wxReqDTO, HttpServletResponse response) throws IOException {
        saveMsg(wxReqDTO);
        RespUtil.output(response,execute(wxReqDTO,response));
    }

    @Async
    protected void saveMsg(WxReqDTO wxReqDTO) {
        try {
            WeixinMsgDO weixinMsgDO = createWeixinMsg(wxReqDTO);
            if(weixinMsgDO!=null) {
                int i = weixinMsgDao.insert(weixinMsgDO);
                if(i<=0) {
                    logger.error("插入数据失败");
                }
            }
        } catch (Exception e) {
            logger.error("插入数据异常！",e);
        }

    }

    private WeixinMsgDO createWeixinMsg(WxReqDTO wxReqDTO) {
        if(wxReqDTO==null) {
            return null;
        }

        WeixinMsgDO weixinMsgDO = new WeixinMsgDO();
        WxBaseDTO reqDTO = wxReqDTO.getWxXmlDO();
        if(reqDTO!=null) {
            weixinMsgDO.setMsgType(reqDTO.getMsgType());
            weixinMsgDO.setFromUserName(reqDTO.getFromUserName());
            weixinMsgDO.setToUserName(reqDTO.getToUserName());
            if(StringUtils.isNotBlank(reqDTO.getCreateTime())) {
                weixinMsgDO.setCreateTime(Long.valueOf(reqDTO.getCreateTime()));
            }
            weixinMsgDO.setMsgId(reqDTO.getMsgId());
            weixinMsgDO.setRawText(reqDTO.getRawText());
        }

        QueryStrDO queryStrDO = wxReqDTO.getQueryStrDO();
        if(queryStrDO!=null) {
            weixinMsgDO.setQueryStr(queryStrDO.getQueryString());
        }
        return weixinMsgDO;
    }



    protected abstract String execute(WxReqDTO wxReqDTO, HttpServletResponse response) throws IOException;

    protected String createDefaultResp(WxReqDTO wxReqDTO) {
        try {
            XmlType type = wxReqDTO.getWxXmlDO().getType();
            if(XmlType.TEXT.equals(type)) {
                return createTextResp(wxReqDTO);
            }
            if(XmlType.IMAGE.equals(type)) {
                WxImageDTO wxImageDTO = (WxImageDTO)wxReqDTO.getWxXmlDO();
                return RespUtil.createImageResp(wxImageDTO.getFromUserName(),wxImageDTO.getMediaId());
            }
            return RespUtil.createTextResp(wxReqDTO.getWxXmlDO().getFromUserName(),wxReqDTO.toString());
        } catch (Exception ex) {
            logger.error("parse error:"+wxReqDTO.getWxXmlDO(),ex);
        }
        return createTextResp(wxReqDTO);
    }

    protected String createTextResp(WxReqDTO wxReqDTO) {
        WxBaseDTO wxbaseDTO = wxReqDTO.getWxXmlDO();
        String content;
        String respText;
        if(wxbaseDTO instanceof WxTextDTO) {
            respText = ((WxTextDTO)wxbaseDTO).getContent()+" /::D";
        } else {
            respText = "哦豁，有地方出问题咯";
        }
        return RespUtil.createTextResp(wxbaseDTO.getFromUserName(),respText);
    }

}
