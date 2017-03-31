package com.henry.weixin.wxapi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.henry.weixin.common.annotation.ReqDesc;
import com.henry.weixin.handler.XmlMsgHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/11.
 *
 * @author sanfen.yf
 * @date 2017/03/11
 */
@Component
public class BaseApi implements ApplicationContextAware{

    protected final static Logger logger = LoggerFactory.getLogger(BaseApi.class);

    private static Map<String,Pair> commandMapper;

    private ApplicationContext applicationContext;

    public static String execute(String command,Object...objects) {
        if(StringUtils.isBlank(command)||commandMapper==null) {
            return null;
        }
        Pair pair = commandMapper.get(command);
        if(pair==null) {
            return null;
        }
        BaseService baseService = pair.baseService;
        Method method = pair.method;
        if(baseService==null||method==null) {
            logger.error("class and method are null!");
            return null;
        }
        try {
            return (String)method.invoke(baseService,objects);
        } catch (IllegalAccessException e) {
            logger.error("invoke error!"+pair,e);
        } catch (InvocationTargetException e) {
            logger.error("invoke error!"+pair,e);
        }
        return null;
    }

    @PostConstruct
    private void init() {
        commandMapper = Maps.newHashMap();
        Map<String,BaseService> beanMap = applicationContext.getBeansOfType(BaseService.class);
        if(beanMap!=null) {
            Iterator<Map.Entry<String,BaseService>> itr = beanMap.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry<String,BaseService> entry = itr.next();
                BaseService baseApi = entry.getValue();
                Method[] methods = baseApi.getClass().getDeclaredMethods();
                for(Method m:methods) {
                    ReqDesc reqDesc = m.getAnnotation(ReqDesc.class);
                    if(reqDesc==null) {
                        continue;
                    }
                    String command = reqDesc.command();
                    if(StringUtils.isBlank(command)) {
                        logger.error("empty command for this method:{},{}",baseApi.getClass().getName(),m.getName());
                    }
                    Pair a = commandMapper.get(command);
                    if(a!=null) {
                        logger.error("duplicate command={},{},{}",command,baseApi.getClass().getName(),m.getName(),a);
                        continue;
                    }
                    commandMapper.put(command,new Pair(reqDesc,baseApi,m));
                }
            }
        }
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

    private static class Pair {
        ReqDesc reqDesc;
        Method method;
        BaseService baseService;

        Pair(ReqDesc reqDesc,BaseService baseService,Method m) {
            this.reqDesc = reqDesc;
            method = m;
            this.baseService = baseService;
        }
        @Override
        public String toString() {
            String ret = "";
            if(baseService!=null) {
                ret = baseService.getClass().getName()+",";
            }
            if(method!=null) {
                ret += method.toString();
            }
            if(StringUtils.isNotBlank(ret)) {
                return ret;
            }
            return super.toString();
        }
    }
}
