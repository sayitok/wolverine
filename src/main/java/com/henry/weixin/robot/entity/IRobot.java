package com.henry.weixin.robot.entity;

import java.util.Map;

import com.henry.weixin.robot.model.RobotResp;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public interface IRobot {

    RobotResp initMembers(String memberList, Map<String,Object> paras);

    RobotResp handleMsg(String content, Map<String,Object> paras);

    RobotResp exit(Map<String,Object> paras);
}
