package com.henry.weixin.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.henry.weixin.common.enums.XmlType;
import com.henry.weixin.common.utils.RespUtil;
import com.henry.weixin.common.utils.UrlParser;
import com.henry.weixin.dao.PfPackDao;
import com.henry.weixin.domain.UrlDesc;
import com.henry.weixin.domain.WxBaseDTO;
import com.henry.weixin.domain.WxLinkDTO;
import com.henry.weixin.domain.WxReqDTO;
import com.henry.weixin.domain.WxTextDTO;
import com.henry.weixin.domain.db.PfPackDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
@Component
public class PfPacketHandler extends XmlMsgHandler {

    @Autowired
    private PfPackDao pfPackDao;


    @Override
    protected String execute(WxReqDTO wxReqDTO, HttpServletResponse response) throws IOException {
        WxLinkDTO wxLinkDTO = (WxLinkDTO)wxReqDTO.getWxXmlDO();
        savePfPack(wxLinkDTO);

        return RespUtil.createLinkResp(wxLinkDTO.getFromUserName(),wxLinkDTO.getTitle(),wxLinkDTO.getDescription(),wxLinkDTO.getUrl());
    }

    @Async
    private void savePfPack(WxLinkDTO wxLinkDTO) {
        PfPackDO pfPackDO = parsePfPack(wxLinkDTO);
        if(pfPackDO==null) {
            logger.error("解析浦发红包对象失败={}",wxLinkDTO);
        }
        try {
            int i = pfPackDao.insert(pfPackDO);
            if(i<=0) {
                logger.error("插入数据失败");
            }
        } catch (Exception e) {
            logger.error("插入数据失败",e);
        }
    }


    private PfPackDO parsePfPack(WxLinkDTO wxLinkDTO) {
        if(wxLinkDTO==null) {
            return null;
        }

        PfPackDO pfPackDO = new PfPackDO();
        pfPackDO.setDescription(wxLinkDTO.getDescription());
        pfPackDO.setTitle(wxLinkDTO.getTitle());
        if(StringUtils.isNotBlank(wxLinkDTO.getCreateTime())) {
            pfPackDO.setCreateTime(Long.valueOf(wxLinkDTO.getCreateTime()));
        }
        pfPackDO.setMsgId(wxLinkDTO.getMsgId());
        pfPackDO.setUserId(wxLinkDTO.getFromUserName());
        UrlDesc urlDesc = wxLinkDTO.getUrlDesc();
        if(urlDesc!=null) {
            pfPackDO.setPacketId(urlDesc.getValue("packetId"));
            pfPackDO.setPackStatus(urlDesc.getValue("status"));
        }
        pfPackDO.setStatus(-1);
        return pfPackDO;
    }


    @Override
    public boolean recognize(WxReqDTO wxReqDTO) {
        if(wxReqDTO==null||wxReqDTO.getWxXmlDO()==null) {
            return false;
        }
        if(!XmlType.LINK.equals(wxReqDTO.getPostXmlType())) {
            return false;
        }
        UrlDesc urlDesc = ((WxLinkDTO)(wxReqDTO.getWxXmlDO())).getUrlDesc();
        return urlDesc!=null&&StringUtils.isNotBlank(urlDesc.getDomain())&&urlDesc.getDomain().contains("spdbccc.com");
    }
}
