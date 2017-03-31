package com.henry.weixin.domain;

import com.henry.weixin.common.enums.XmlType;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public class WxTextDTO extends WxBaseDTO {


    private static final long serialVersionUID = 2005934615123291984L;

    private String content;

    public WxTextDTO() {

    }

    public WxTextDTO(String text) {
        super(text);
    }

    @Override
    public XmlType getType() {
        return XmlType.TEXT;
    }

    @Override
    public Document toXmlDocument(Document document) {
        Document doc = super.toXmlDocument(document);
        addElement(doc.getRootElement(),"Content",this.getContent());
        return doc;
    }

    @Override
    protected void parseElement(Element xmlRoot) {
        super.parseElement(xmlRoot);
        if(xmlRoot!=null) {
            this.setContent(getElementText(xmlRoot,"Content"));
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
