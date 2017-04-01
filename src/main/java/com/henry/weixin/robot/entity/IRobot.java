package com.henry.weixin.robot.entity;

import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.model.WeixinMsg;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public interface IRobot {

    RobotResp initMembers(WeixinMsg weixinMsg);

    RobotResp handleMsg(WeixinMsg weixinMsg);

    RobotResp exit(WeixinMsg weixinMsg);
}
