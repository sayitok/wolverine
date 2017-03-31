package com.henry.weixin.common.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.henry.weixin.common.constant.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/11.
 *
 * @author sanfen.yf
 * @date 2017/03/11
 */
public class AccessTokenUtil {

    protected final static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);

    private static final String KEY_ACCESS_TOKEN = "global_access_token";

    private static final String TOKEN_REQ_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ SysConfig.APP_ID+"&secret="+SysConfig.APP_SECRET;

    private static Integer DEFAULT_EXPIRE_TIME = 3600;

    private static LoadingCache<String, String> globalCacher;

    private AccessTokenUtil() {}

    static {
        init();
    }

    private static void init() {
        globalCacher = CacheBuilder.newBuilder().concurrencyLevel(8)
            .expireAfterWrite(DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS)
            .build(new CacheLoader(){
            @Override
            public Object load(Object o) throws Exception {
                if(o!=null&&o instanceof String) {
                    JSONObject jsonObject = queryAccessToken();
                    if(jsonObject!=null) {
                        return jsonObject.getString("access_token");
                    }
                } else {
                    logger.error("invalid key type={}",o);
                }
                return null;
            }
        });
    }

    public static String getAccessToken() {
        try {
            String token = globalCacher.get(KEY_ACCESS_TOKEN);
            if(token==null) {
                globalCacher.invalidateAll();
                token = globalCacher.get(KEY_ACCESS_TOKEN);
            }
            return token;
        } catch (ExecutionException e) {
            logger.error("查询accessToken发生并发异常",e);
        }
        return null;
    }

    private static JSONObject queryAccessToken() {
        String ret = HttpUtil.sendGet(TOKEN_REQ_URL);
        logger.warn("resp for access token={}",ret);
        if(StringUtils.isBlank(ret)) {
            logger.error("获取accessToken失败！");
            return null;
        }
        return JSONObject.parseObject(ret);
    }
}
