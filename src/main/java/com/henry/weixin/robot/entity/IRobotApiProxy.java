package com.henry.weixin.robot.entity;

import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.model.WeixinMsg;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public interface IRobotApiProxy {

    RobotResp execute(WeixinMsg weixinMsg);
}
