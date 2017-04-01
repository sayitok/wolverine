package com.henry.weixin.robot.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.henry.weixin.robot.RobotConfig;
import com.henry.weixin.robot.enums.ErrorCode;
import com.henry.weixin.robot.enums.RobotApiProxyEnum;
import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.model.WeixinMsg;
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
    public RobotResp initMembers(WeixinMsg weixinMsg) {
        return RobotRespUtil.createSuccessResp("success");
    }

    @Override
    public RobotResp handleMsg(WeixinMsg weixinMsg) {
        if(fromQun(weixinMsg)) {
            return RobotRespUtil.createResp(ErrorCode.SILENTCE.getCode(),null,null);
        }
        RobotApiProxyEnum proxyEnum = RobotConfig.getRobotApiProxyIndex(weixinMsg.getFromUserName(),weixinMsg.getToUserName());
        IRobotApiProxy apiProxy = robotApiProxyMap.get(proxyEnum);
        if(apiProxy!=null) {
            return apiProxy.execute(weixinMsg);
        }
        return RobotRespUtil.createSuccessResp("success");
    }

    private boolean fromQun(WeixinMsg weixinMsg) {
        if(weixinMsg==null) {
            return false;
        }
        return weixinMsg.getFromUserName()!=null&&weixinMsg.getFromUserName().startsWith("@@");
    }

    @Override
    public RobotResp exit(WeixinMsg weixinMsg) {
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
