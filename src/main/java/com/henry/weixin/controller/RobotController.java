package com.henry.weixin.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.henry.weixin.common.utils.RespUtil;
import com.henry.weixin.robot.entity.DefaultRobot;
import com.henry.weixin.robot.enums.MethodEnum;
import com.henry.weixin.robot.model.RobotResp;
import com.henry.weixin.robot.utils.RobotRespUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sanfen.yf on 2017/3/31.
 */
@Controller
@RequestMapping("/handleMsg.do")
public class RobotController {
    protected final static Logger logger = LoggerFactory.getLogger(RobotController.class);

    @Resource
    private DefaultRobot defaultRobot;

    @RequestMapping(method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        handleReq(request,response);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleReq(request,response);
    }

    private void handleReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        MethodEnum methodEnum = MethodEnum.getMethod(request.getParameter("m"));
        if(methodEnum==null) {
            RespUtil.output(response, RobotRespUtil.createErrorResp("参数不正确").toString());
            return;
        }
        RobotResp robotResp = null;
        if(MethodEnum.MEMBER.equals(methodEnum)) {
            robotResp = defaultRobot.initMembers(request.getParameter("para"),buildParamMap(request.getParameterMap()));
        } else if(MethodEnum.MSG.equals(methodEnum)) {
            robotResp = defaultRobot.handleMsg(request.getParameter("para"),buildParamMap(request.getParameterMap()));
        } else {
            robotResp = RobotRespUtil.createErrorResp("m参数不正确");
        }
        RespUtil.output(response, robotResp.toString());
    }

    private Map<String,Object> buildParamMap(Map<String,Object> reqMap) {
        if(reqMap==null) {
            return Maps.newHashMap();
        }
        Map<String,Object> outMap = Maps.newHashMap();
        outMap.putAll(reqMap);
        outMap.remove("para");
        outMap.remove("m");
        return outMap;
    }

}
