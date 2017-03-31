package com.henry.weixin.dao;

import com.henry.weixin.domain.db.WeixinMsgDO;
import org.springframework.stereotype.Repository;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
@Repository
public class WeixinMsgDao extends BaseDAO {

    public int insert(WeixinMsgDO weixinMsgDO) throws Exception {
        if(weixinMsgDO==null) {
            return 0;
        }
        return sessionTemplate.insert("com.henry.weixin.dao.WeixinMsgDao.insert",weixinMsgDO);
    }
}
