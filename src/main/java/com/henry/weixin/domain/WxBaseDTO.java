package com.henry.weixin.domain;

import java.io.Serializable;

import com.henry.weixin.common.enums.XmlType;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public abstract class WxBaseDTO implements Serializable {

    protected final static Logger logger = LoggerFactory.getLogger(WxBaseDTO.class);

    private static final long serialVersionUID = -5044909540345914147L;

    private static final int MAX_LENGTH = 1024*1024*10;


    private String toUserName;

    private String fromUserName;

    private String createTime;

    private String msgType;

    private String msgId;

    protected String rawText;



    public WxBaseDTO() {

    }

    public WxBaseDTO(String text) {
        this.rawText = text;
        parse();
    }

    public abstract XmlType getType();

    public String getRawText() {
        return rawText;
    }

    public static WxBaseDTO parseReqXml(String rawText) {
        if(StringUtils.isBlank(rawText)) {
            logger.warn("parse xml text:Empty content!");
            return null;
        }
        int length = rawText.length();
        if(length>MAX_LENGTH) {
            logger.warn("content length is oversize:{}",length);
            return null;
        }
        try {
            Document document = DocumentHelper.parseText(rawText);
            Element xmlRoot = document.getRootElement();
            String msgType = getElementText(xmlRoot,"MsgType");
            WxBaseDTO wxContentDO = createInstance(XmlType.getReqType(msgType));
            if(wxContentDO!=null) {
                wxContentDO.parse(document);
            }
            return wxContentDO;
        } catch (DocumentException e) {
            logger.error("parse xml error:"+rawText,e);
        }
        return null;
    }



    public static WxBaseDTO createInstance(XmlType reqType) {
        if(reqType==null) {
            return new WxRawDTO();
        }
        Class<? extends WxBaseDTO> clazz = reqType.getClazz();
        if(clazz!=null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                logger.error("初始化失败",e);
            } catch (IllegalAccessException e) {
                logger.error("初始化失败",e);
            }
        }
        return new WxRawDTO();
    }

    protected Document parse(Document document) {
        if(document==null) {
            return null;
        }

        this.rawText = document.asXML();
        parseElement(document.getRootElement());
        return document;
    }

    protected void parseElement(Element xmlRoot) {
        if(xmlRoot!=null) {
            this.setCreateTime(getElementText(xmlRoot,"CreateTime"));
            this.setFromUserName(getElementText(xmlRoot,"FromUserName"));
            this.setMsgId(getElementText(xmlRoot,"MsgId"));
            this.setMsgType(getElementText(xmlRoot,"MsgType"));
            this.setToUserName(getElementText(xmlRoot,"ToUserName"));
        }

    }

    protected Document parse() {
        if(StringUtils.isBlank(rawText)) {
            return null;
        }
        try {
            Document document = DocumentHelper.parseText(rawText);
            parseElement(document.getRootElement());
            return document;
        } catch (DocumentException e) {
            logger.error("parse xml error:"+rawText,e);
        }
        return null;
    }

    public static String getElementText(Element element,String elementName) {
        if(element==null||StringUtils.isBlank(elementName)) {
            return null;
        }
        Element subElement = element.element(elementName);
        if(subElement==null) {
            logger.warn("no element named {}",elementName);
            return null;
        }
        return subElement.getTextTrim();
    }

    public Document toXmlDocument(Document document) {
        Element root = DocumentHelper.createElement("xml");
        document.setRootElement(root);
        addElement(root,"CreateTime",this.getCreateTime());
        addElement(root,"FromUserName",this.getFromUserName());
        addElement(root,"ToUserName",this.getToUserName());
        addElement(root,"MsgId",this.getMsgId());
        addElement(root,"MsgType",this.getMsgType());
        return document;
    }

    protected void addElement(Element root,String name,String value) {
        if(root==null||StringUtils.isBlank(name)||StringUtils.isBlank(value)) {
            return;
        }
        Element element = DocumentHelper.createElement(name);
        element.setText(value);
        root.add(element);
    }

    public static Document generateXml(WxBaseDTO contentDTO) {
        if(contentDTO==null) {
            return DocumentHelper.createDocument();
        }
        return contentDTO.toXmlDocument(DocumentHelper.createDocument());
    }


    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }



    @Override
    public String toString() {
        return toXmlDocument(DocumentHelper.createDocument()).asXML();
    }
}
