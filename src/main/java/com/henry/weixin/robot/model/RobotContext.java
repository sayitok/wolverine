package com.henry.weixin.robot.model;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
public class RobotContext implements Serializable {
    private static final long serialVersionUID = 5872392627929377606L;

    private static RobotContext robotContext;

    private Map<String,Member> memberMap;

    private RobotContext() {
        memberMap = Maps.newHashMap();
    }

    public static RobotContext getInstance() {
        return RobotContextWrapper.getRobotContext();
    }

    private static class RobotContextWrapper {
        static RobotContext getRobotContext() {
            if(robotContext==null) {
                robotContext = new RobotContext();
            }
            return robotContext;
        }
    }
}
