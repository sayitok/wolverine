package com.henry.weixin.robot.entity;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Lists;
import com.henry.weixin.common.utils.HttpUtil;
import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.model.WeixinMsg;
import com.henry.weixin.robot.utils.RobotRespUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
@Component
public class SimSimiApiProxy implements IRobotApiProxy{

    protected final static Logger logger = LoggerFactory.getLogger(SimSimiApiProxy.class);

    private List<String> trialKeyList;

    private int currentIndex;

    private static final float defaultLc = 0.8f;

    private String host = "http://sandbox.api.simsimi.com/request.p?";

    private String queryStr = "key=%s&lc=ch&ft=%.1f&text=%s";

    @PostConstruct
    private void init() {
        trialKeyList = Lists.newArrayList();
        trialKeyList.add("427337af-9251-4aa6-8737-ba7584e19d6d");
        trialKeyList.add("ad4c8af3-b015-4cc2-98a6-53c01aae2a8d");
        trialKeyList.add("bab672cd-60a9-4959-b378-7f0cde2882e9");
        currentIndex = 0;
    }

    @Override
    public RobotResp execute(WeixinMsg weixinMsg) {
        return run(weixinMsg.getContent());
    }

    public RobotResp run(String content) {
        String url = getReqUrl(content,defaultLc);
        String result = HttpUtil.sendGet(url,"utf-8");
        if(StringUtils.isBlank(result)) {
            return RobotRespUtil.createSuccessResp("С�Ƽ�˯��������Ҫ��С������������");
        }
        logger.warn("�յ�С�Ƽ���Ϣ��={}",result);
        JSONObject json = JSONObject.parseObject(result);
        Integer retCode = json.getInteger("result");
        String resp = json.getString("response");
        String msg = json.getString("msg");
        if(retCode==null) {
            return RobotRespUtil.createSuccessResp("С�Ƽ������ˣ��ȿ�");
        }
        if(retCode==100) {
            if(StringUtils.isBlank(resp)) {
                resp = "����һֻСѼ��ѽ��һѾһѾӴ";
            }
            return RobotRespUtil.createSuccessResp(resp);
        }
        if(retCode==509) {
            nextIndex();
            return RobotRespUtil.createSuccessResp("С�Ƽ�����ȥ�ˣ��͹���ҪС������");
        }
        if(retCode==404) {
            return RobotRespUtil.createSuccessResp("����ʲô��������~~~");
        }
        return RobotRespUtil.createSuccessResp("ϵͳ�������ϣ�����ϵ���˴���һ��,����:"+result);
    }

    private String getReqUrl(String content,float lc) {
        try {
            return host+ String.format(queryStr,trialKeyList.get(currentIndex),lc,URLEncoder.encode(content,"utf-8"));
        } catch (Exception e) {
            return host+ String.format(queryStr,trialKeyList.get(currentIndex),lc,content);
        }

    }

    private int nextIndex() {
        currentIndex++;
        currentIndex%=3;
        return currentIndex;
    }
}
