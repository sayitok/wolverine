package com.henry.weixin.common.utils;

import com.henry.weixin.domain.UrlDesc;
import com.henry.weixin.handler.XmlMsgHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
public class UrlParser {

    protected final static Logger logger = LoggerFactory.getLogger(UrlParser.class);

    private UrlParser() {

    }

    public static UrlDesc parseUrl(String url) {
        try {
            if(StringUtils.isBlank(url)) {
                return null;
            }
            UrlDesc urlDesc = new UrlDesc(url);
            String tmpUrl = url.trim();
            int idx = tmpUrl.indexOf(':');
            urlDesc.setProtocol(tmpUrl.substring(0,idx));
            tmpUrl = tmpUrl.substring(idx+3);
            idx = tmpUrl.indexOf('/');
            if(idx<=0) {
                urlDesc.setDomain(tmpUrl);
            } else {
                urlDesc.setDomain(tmpUrl.substring(0,idx));
                tmpUrl = tmpUrl.substring(idx+1);
                idx = tmpUrl.indexOf('?');
                if(idx<=0) {
                    urlDesc.setPath(tmpUrl);
                } else {
                    urlDesc.setPath(tmpUrl.substring(0,idx));
                    tmpUrl = tmpUrl.substring(idx+1);
                    String[] qs = tmpUrl.split("=&");
                    if(qs!=null&&qs.length>=2) {
                        for(int i=0;i<qs.length;i+=2) {
                            urlDesc.addQueryString(qs[i],qs[i+1]);
                        }
                    }
                }
            }
            return urlDesc;
        } catch (Exception e) {
            logger.error("parse url error="+url,e);
        }
        return null;
    }
}
