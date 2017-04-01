package com.henry.weixin.robot.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public class WeixinMsg implements Serializable {

    protected final static Logger logger = LoggerFactory.getLogger(WeixinMsg.class);

    private static final long serialVersionUID = 2582931507464905182L;

    private String msgId;

    private String fromUserId;

    private String toUserId;

    private int msgType;

    private String content;

    private long createTime;

    private String newMsgId;

    private String rawData;

    private String fromUserName;



    public WeixinMsg(String raw) {
        this.rawData = raw;
        parse(this.rawData);
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void parse(String rawData) {
        if(StringUtils.isBlank(rawData)) {
            return;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(rawData);
            msgId = jsonObject.getString("MsgId");
            fromUserId = jsonObject.getString("FromUserName");
            toUserId = jsonObject.getString("ToUserName");
            msgType = jsonObject.getIntValue("MsgType");
            content = jsonObject.getString("Content");
            createTime = jsonObject.getLongValue("CreateTime");
            newMsgId = jsonObject.getString("NewMsgId");
        } catch (Exception e) {
            logger.error("½âÎöÊý¾ÝÊ§°Ü:"+rawData,e);
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }


    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNewMsgId() {
        return newMsgId;
    }

    public void setNewMsgId(String newMsgId) {
        this.newMsgId = newMsgId;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
