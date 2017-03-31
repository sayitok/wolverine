package com.henry.weixin.domain;

import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.common.utils.UrlParser;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public class WxLinkDTO extends WxBaseDTO {
    private static final long serialVersionUID = -8999110188162358670L;

    private String title;

    private String description;


    private UrlDesc urlDesc;
    private String url;


    @Override
    public XmlType getType() {
        return XmlType.LINK;
    }

    @Override
    public Document toXmlDocument(Document document) {
        Document doc = super.toXmlDocument(document);
        addElement(doc.getRootElement(),"Url",this.getUrl());
        addElement(doc.getRootElement(),"Title",this.getTitle());
        addElement(doc.getRootElement(),"Description",this.getDescription());
        return doc;
    }

    @Override
    protected void parseElement(Element xmlRoot) {
        super.parseElement(xmlRoot);
        if(xmlRoot!=null) {
            this.setUrl(getElementText(xmlRoot,"Url"));
            this.setTitle(getElementText(xmlRoot,"Title"));
            this.setDescription(getElementText(xmlRoot,"Description"));
        }
        urlDesc = UrlParser.parseUrl(this.getUrl());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public UrlDesc getUrlDesc() {
        return urlDesc;
    }

    public void setUrlDesc(UrlDesc urlDesc) {
        this.urlDesc = urlDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
