package com.henry.weixin.robot.entity;

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

    private String host = "http://sandbox.api.simsimi.com/request.p?key=%s&lc=ch&ft=%.1f&text=%s";

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
        String url = getReqUrl(weixinMsg.getContent(),defaultLc);
        String result = HttpUtil.sendGet(url);
        if(StringUtils.isBlank(result)) {
            return RobotRespUtil.createSuccessResp("小黄鸡睡着啦，需要叫小豆出来陪你吗？");
        }
        logger.warn("收到小黄鸡消息啦={}",result);
        JSONObject json = JSONObject.parseObject(result);
        Integer retCode = json.getInteger("result");
        String resp = json.getString("response");
        String msg = json.getString("msg");
        if(retCode==null) {
            return RobotRespUtil.createSuccessResp("小黄鸡生病了，咳咳");
        }
        if(retCode==100) {
            return RobotRespUtil.createSuccessResp(resp);
        }
        if(retCode==509) {
            nextIndex();
            return RobotRespUtil.createSuccessResp("小黄鸡出差去了，客官需要小豆陪吗");
        }
        return RobotRespUtil.createSuccessResp("系统发生故障，快联系主人处理一下,咔咔:"+result);
    }

    private String getReqUrl(String content,float lc) {
        return String.format(host,trialKeyList.get(currentIndex),lc,content);
    }

    private int nextIndex() {
        currentIndex++;
        currentIndex%=3;
        return currentIndex;
    }
}
