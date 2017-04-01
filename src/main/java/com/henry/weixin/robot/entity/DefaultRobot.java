package com.henry.weixin.robot.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.henry.weixin.robot.RobotConfig;
import com.henry.weixin.robot.enums.RobotApiProxyEnum;
import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.utils.RobotRespUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
@Service
public class DefaultRobot implements IRobot,ApplicationContextAware,InitializingBean{

    private ApplicationContext applicationContext;

    private Map<RobotApiProxyEnum,IRobotApiProxy> robotApiProxyMap;

    @Override
    public RobotResp initMembers(String memberList, Map<String, Object> paras) {
        return RobotRespUtil.createSuccessResp("success");
    }

    @Override
    public RobotResp handleMsg(String content, Map<String, Object> paras) {
        RobotApiProxyEnum proxyEnum = RobotConfig.getRobotApiProxyIndex("","");
        IRobotApiProxy apiProxy = robotApiProxyMap.get(proxyEnum);
        if(apiProxy!=null) {
            return apiProxy.execute(content,paras);
        }
        return RobotRespUtil.createSuccessResp("success");
    }

    @Override
    public RobotResp exit(Map<String, Object> paras) {
        return RobotRespUtil.createSuccessResp("success");
    }

    /**
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        robotApiProxyMap = Maps.newHashMap();
        robotApiProxyMap.put(RobotApiProxyEnum.SIMSIMI,(SimSimiApiProxy)applicationContext.getBean("simSimiApiProxy"));
    }
}
