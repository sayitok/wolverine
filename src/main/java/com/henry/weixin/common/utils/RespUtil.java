package com.henry.weixin.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.common.constant.SysConfig;
import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxImageDTO;
import com.henry.weixin.domain.WxTextDTO;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/9.
 *
 * @author sanfen.yf
 * @date 2017/03/09
 */
public class RespUtil {

    protected final static Logger logger = LoggerFactory.getLogger(RespUtil.class);

    private RespUtil() {}

    public static String createTextResp(String toUserName,String content) {
        WxTextDTO wxTextDTO = (WxTextDTO)createBaseResp(toUserName,XmlType.TEXT);
        wxTextDTO.setContent(content);
        return wxTextDTO.toString();
    }

    public static String createLinkResp(String toUserName, String title,String desc,String link) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(",").append(desc).append(":").append(link);
        return createTextResp(toUserName,sb.toString());
    }

    public static String createImageResp(String toUserName, String mediaId) {
        WxImageDTO resp = (WxImageDTO)createBaseResp(toUserName,XmlType.IMAGE);
        Document document = resp.toXmlDocument(DocumentHelper.createDocument());
        Element element = DocumentHelper.createElement("Image");
        Element sub = DocumentHelper.createElement("MediaId");
        sub.setText(mediaId);
        element.add(sub);
        document.getRootElement().add(element);
        return document.asXML();
    }

    protected static WxBaseDTO createBaseResp(String toUserName,XmlType xmlType) {
        WxBaseDTO respDTO = WxBaseDTO.createInstance(xmlType);
        respDTO.setToUserName(toUserName);
        respDTO.setFromUserName(SysConfig.DEVELOPER_ID);
        respDTO.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
        respDTO.setMsgType(respDTO.getType().getType());
        return respDTO;
    }

    public static String parsePostReq(HttpServletRequest request) throws IOException {
        /** 读取接收到的xml消息 */
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void output(HttpServletResponse response, String result) {
        if(result==null) {
            result = "empty";
        }
        logger.warn("output={}",result);
        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("exception happens!",e);
        }
    }

    public static void output(HttpServletResponse response, String result,String charset) {
        if(result==null) {
            result = "empty";
        }
        if(StringUtils.isBlank(charset)) {
            charset = "UTF-8";
        }
        logger.warn("output={}",result);
        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes(charset));
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("exception happens!",e);
        }
    }

    public static WxBaseDTO buildTextResp(String toUser, String content) {
        WxTextDTO respDTO = new WxTextDTO();
        respDTO.setToUserName(toUser);
        respDTO.setFromUserName(SysConfig.DEVELOPER_ID);
        respDTO.setCreateTime(String.valueOf(System.currentTimeMillis()));
        respDTO.setMsgType(respDTO.getType().getType());
        if(StringUtils.isBlank(content)) {
            content = "啊哦，无法正常工作，肯定有什么不好的事情发生了";
        }
        respDTO.setContent(content);
        return respDTO;
    }
}
