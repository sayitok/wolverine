package com.henry.weixin.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
public class UrlDesc implements Serializable {

    private static final long serialVersionUID = -1280174222030719576L;
    private String url;

    private String protocol;

    private String domain;

    private String path;

    private Map<String,String> queryStringMap;


    public UrlDesc(String url) {
        this.url = url;
        queryStringMap = new HashMap<>();
    }

    public Map<String, String> getQueryStringMap() {
        return queryStringMap;
    }

    public String getValue(String key) {
        if(StringUtils.isNotBlank(key)) {
            return queryStringMap.get(key);
        }
        return null;
    }

    public void addQueryString(String key,String val) {
        if(StringUtils.isBlank(key)) {
            return;
        }
        queryStringMap.put(key,val);
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }




    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    @Override
    public String toString() {
        return url;
    }
}
