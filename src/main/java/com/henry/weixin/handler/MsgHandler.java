package com.henry.weixin.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.domain.WxReqDTO;

/**
 * Created by sanfen.yf on 2017/3/4.
 *
 * @author sanfen.yf
 * @date 2017/03/04
 */
public interface MsgHandler {

    public void process(WxReqDTO wxReqDTO, HttpServletResponse response) throws IOException ;

    public boolean recognize(WxReqDTO wxReqDTO);
}
