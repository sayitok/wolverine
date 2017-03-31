package com.henry.weixin.domain;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.henry.weixin.common.enums.HttpMethod;
import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.common.utils.RespUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/5.
 *
 * @author sanfen.yf
 * @date 2017/03/05
 */
public class WxReqDTO implements Serializable {

    protected final static Logger logger = LoggerFactory.getLogger(WxReqDTO.class);

    private static final long serialVersionUID = 7288918640160741799L;

    private QueryStrDO queryStrDO;

    private WxBaseDTO wxXmlDO;

    private HttpMethod httpMethod;

    private HttpServletRequest servletRequest;

    public WxReqDTO(HttpServletRequest request, HttpMethod method) {
        this.httpMethod = method;
        this.servletRequest = request;
        parse();
    }

    public XmlType getPostXmlType() {
        return wxXmlDO!=null?wxXmlDO.getType():null;
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    private void parse(){
        QueryStrDO queryStrDO = new QueryStrDO(servletRequest.getQueryString());
        queryStrDO.setEchostr(servletRequest.getParameter("echostr"));
        queryStrDO.setNonce(servletRequest.getParameter("nonce"));
        queryStrDO.setSignature(servletRequest.getParameter("signature"));
        queryStrDO.setTimestamp(servletRequest.getParameter("timestamp"));
        queryStrDO.setOpenId(servletRequest.getParameter("openId"));
        this.setQueryStrDO(queryStrDO);
        if(HttpMethod.POST.equals(httpMethod)) {
            try {
                String xml = RespUtil.parsePostReq(servletRequest);
                this.setWxXmlDO(WxBaseDTO.parseReqXml(xml));
            } catch (IOException e) {
                logger.error("parse xml error!",e);
            }
        }
    }

    public boolean isGetReq() {
        return HttpMethod.GET.equals(httpMethod);
    }

    public boolean isEchoReq() {
        return StringUtils.isNotEmpty(getEchoStr());
    }

    public String getEchoStr() {
        return queryStrDO!=null?queryStrDO.getEchostr():null;
    }

    public QueryStrDO getQueryStrDO() {
        return queryStrDO;
    }

    public void setQueryStrDO(QueryStrDO queryStrDO) {
        this.queryStrDO = queryStrDO;
    }

    public WxBaseDTO getWxXmlDO() {
        return wxXmlDO;
    }

    public void setWxXmlDO(WxBaseDTO wxXmlDO) {
        this.wxXmlDO = wxXmlDO;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(httpMethod).append(":").append(queryStrDO).append(",").append(wxXmlDO);
        return sb.toString();
    }
}
