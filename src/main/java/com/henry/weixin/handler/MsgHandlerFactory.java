package com.henry.weixin.handler;

import java.util.ArrayList;
import java.util.List;

import com.henry.weixin.domain.WxReqDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/5.
 *
 * @author sanfen.yf
 * @date 2017/03/05
 */
@Component
public class MsgHandlerFactory implements ApplicationContextAware,InitializingBean {

    private ApplicationContext applicationContext;

    private MsgHandlerFactory() {}

    private static MsgHandlerFactory instance;

    private static List<MsgHandler> handlerList;

    private static MsgHandlerFactory getInstance() {
        return MsgHandlerFactory.instance;
    }

    private static MsgHandler DEFAULT_HANDLER;

    private void init() {
        handlerList = new ArrayList<>();
        DEFAULT_HANDLER = (MsgHandler)applicationContext.getBean("defaultMsgHandler");
        handlerList.add((MsgHandler)applicationContext.getBean("pfPacketHandler"));
        handlerList.add(DEFAULT_HANDLER);
    }

    public static MsgHandler getHandler(WxReqDTO wxReqDTO) {
        if(wxReqDTO==null) {
            return null;
        }
        for(MsgHandler msgHandler:handlerList) {
            if(msgHandler.recognize(wxReqDTO)) {
                return msgHandler;
            }
        }
        return DEFAULT_HANDLER;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = (MsgHandlerFactory) applicationContext.getBean("msgHandlerFactory");
        init();
    }
}
