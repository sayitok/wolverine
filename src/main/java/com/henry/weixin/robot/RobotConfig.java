package com.henry.weixin.robot;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.henry.weixin.robot.enums.RobotApiProxyEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sanfen.yf on 2017/4/1.
 */
public class RobotConfig {

    private RobotConfig() {}

    public static String GROUP_BFBZ = "◊Û≈Û”“”—¥∫≈Øª®ø™";

    public static String OWNER_ID = "@25904efb7ec3c3df0cbb51b4d950419a";

    /**
     * 0-–°∂π,1-–°ª∆º¶
     */
    public static RobotApiProxyEnum dutyRobot = RobotApiProxyEnum.SIMSIMI;

    public static List<String> WHITE_GROUPS = Lists.newArrayList();

    private static Map<String,RobotApiProxyEnum> userDutyRobotMapper = Maps.newHashMap();


    static {
        WHITE_GROUPS.add(GROUP_BFBZ);
    }

    public static boolean whiteGroup(String userId) {
        return userId!=null&&WHITE_GROUPS.contains(userId);
    }

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
