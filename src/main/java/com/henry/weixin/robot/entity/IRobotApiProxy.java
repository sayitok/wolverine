package com.henry.weixin.robot.entity;

import java.util.Map;

import com.henry.weixin.robot.model.RobotResp;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public interface IRobotApiProxy {

    RobotResp execute(String content, Map<String, Object> paras);
}
