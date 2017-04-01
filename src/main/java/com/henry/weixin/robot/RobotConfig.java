package com.henry.weixin.robot;

import java.util.Map;

import com.google.common.collect.Maps;
import com.henry.weixin.robot.enums.RobotApiProxyEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public class RobotConfig {

    private RobotConfig() {}


    /**
     * 0-Ð¡¶¹,1-Ð¡»Æ¼¦
     */
    public static RobotApiProxyEnum dutyRobot = RobotApiProxyEnum.SIMSIMI;

    private static Map<String,RobotApiProxyEnum> userDutyRobotMapper = Maps.newHashMap();

    /**
     *
     * @param fromUser
     * @param toUser
     * @return
     */
    public static RobotApiProxyEnum getRobotApiProxyIndex(String fromUser,String toUser) {
        if(StringUtils.isBlank(fromUser)&&StringUtils.isBlank(toUser)) {
            return dutyRobot;
        }
        RobotApiProxyEnum idx = userDutyRobotMapper.get(fromUser);

        if(idx==null) {
            idx = dutyRobot;
        }
        return idx;
    }

    public static void putUserDuty(String userId,RobotApiProxyEnum proxyEnum) {
        userDutyRobotMapper.put(userId,proxyEnum);
    }
}
