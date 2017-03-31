package com.henry.weixin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.common.enums.HttpMethod;
import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.common.utils.HttpUtil;
import com.henry.weixin.common.utils.RespUtil;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxReqDTO;
import com.henry.weixin.handler.MsgHandler;
import com.henry.weixin.handler.MsgHandlerFactory;
import com.henry.weixin.wxapi.BaseApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sanfen.yf on 2017/3/7.
 *
 * @author sanfen.yf
 * @date 2017/03/07
 */
@Controller
@RequestMapping("/pf.do")
public class WeixinMsgController {
    protected final static Logger logger = LoggerFactory.getLogger(WeixinMsgController.class);


    @RequestMapping(method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleReq(request,response, HttpMethod.GET);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleReq(request,response,HttpMethod.POST);
    }

    private void handleReq(HttpServletRequest request, HttpServletResponse response,HttpMethod method) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        WxReqDTO wxReqDTO = new WxReqDTO(request,method);
        logger.warn("recive:{}",wxReqDTO);
        /** �ж��Ƿ���΢�Ž��뼤����֤��ֻ���״ν�����֤ʱ�Ż��յ�echostr��������ʱ��Ҫ����ֱ�ӷ��� */
        String echostr = wxReqDTO.getEchoStr();
        if (StringUtils.isNotBlank(echostr)||wxReqDTO.isGetReq()) {
            String result = echostr;
            if(StringUtils.isBlank(result)) {
                result = "empty echo!";
            }
            RespUtil.output(response,result);
        } else {
            //������΢�Ŵ�������
            MsgHandler msgHandler = MsgHandlerFactory.getHandler(wxReqDTO);
            if(msgHandler==null) {
                logger.error("unknown req type!");
                RespUtil.output(response,
                    RespUtil.buildTextResp(wxReqDTO.getWxXmlDO().getFromUserName(),"�еط��������ˣ��Ͻ�����һ��").toString());
                return;
            }
            msgHandler.process(wxReqDTO,response);
        }
    }

    private String testHttpReq(String url) {
        return HttpUtil.sendGet(url);
    }
}
