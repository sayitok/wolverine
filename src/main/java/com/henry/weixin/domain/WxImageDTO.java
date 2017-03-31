package com.henry.weixin.domain;

import com.henry.weixin.common.enums.XmlType;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by sanfen.yf on 2017/3/9.
 *
 * @author sanfen.yf
 * @date 2017/03/09
 */
public class WxImageDTO extends WxBaseDTO {

    private static final long serialVersionUID = 5528603073776637660L;
    private String picUrl;

    private String mediaId;

    @Override
    public Document toXmlDocument(Document document) {
        Document doc = super.toXmlDocument(document);
        addElement(doc.getRootElement(),"PicUrl",this.getPicUrl());
        addElement(doc.getRootElement(),"MediaId",this.getMediaId());
        return doc;
    }

    @Override
    protected void parseElement(Element xmlRoot) {
        super.parseElement(xmlRoot);
        if(xmlRoot!=null) {
            this.setPicUrl(getElementText(xmlRoot,"PicUrl"));
            this.setMediaId(getElementText(xmlRoot,"MediaId"));
        }
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public XmlType getType() {
        return XmlType.IMAGE;
    }
}
