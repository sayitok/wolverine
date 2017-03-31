package com.henry.weixin.robot.entity;

import java.util.Map;

import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.utils.RobotRespUtil;
import org.springframework.stereotype.Service;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
@Service
public class DefaultRobot implements IRobot {

    @Override
    public RobotResp initMembers(String memberList, Map<String, Object> paras) {
        return RobotRespUtil.createSuccessResp("success");
    }

    @Override
    public RobotResp handleMsg(String content, Map<String, Object> paras) {
        return RobotRespUtil.createSuccessResp("success");
    }

    @Override
    public RobotResp exit(Map<String, Object> paras) {
        return RobotRespUtil.createSuccessResp("success");
    }
}
