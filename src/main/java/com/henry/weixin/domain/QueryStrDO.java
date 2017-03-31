package com.henry.weixin.domain;

import java.io.Serializable;

/**
 * Created by sanfen.yf on 2017/3/5.
 *
 * @author sanfen.yf
 * @date 2017/03/05
 */
public class QueryStrDO implements Serializable {

    private static final long serialVersionUID = 7077978709355523001L;

    private String signature;

    private String echostr;

    private String timestamp;

    private String nonce;

    private String openId;

    private String queryString;

    public QueryStrDO(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return this.queryString;
    }
}
