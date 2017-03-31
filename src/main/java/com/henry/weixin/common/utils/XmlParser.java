package com.henry.weixin.common.utils;

import com.henry.weixin.domain.WxBaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public class XmlParser {

    protected final static Logger logger = LoggerFactory.getLogger(XmlParser.class);

    private XmlParser() {
    }

    public static WxBaseDTO parseWeixinText(String content) {
        return WxBaseDTO.parseReqXml(content);
    }


    public static void main(String []args) {
        String content = "<xml>\n" +
                "    <URL>\n" +
                "        <![CDATA[http://115.159.209.49/web/pf.do]]>\n" +
                "    </URL>\n" +
                "    <ToUserName>\n" +
                "        <![CDATA[Togoforit]]>\n" +
                "    </ToUserName>\n" +
                "    <FromUserName>\n" +
                "        <![CDATA[Togoforit]]>\n" +
                "    </FromUserName>\n" +
                "    <CreateTime>2017881111</CreateTime>\n" +
                "    <MsgType>\n" +
                "        <![CDATA[text]]>\n" +
                "    </MsgType>\n" +
                "    <Content>\n" +
                "        <![CDATA[helloworld]]>\n" +
                "    </Content>\n" +
                "    <MsgId>111113333</MsgId>\n" +
                "</xml>";
        WxBaseDTO wxTextDTO = parseWeixinText(content);
    }
}
