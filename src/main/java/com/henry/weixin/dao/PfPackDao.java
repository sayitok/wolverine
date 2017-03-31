package com.henry.weixin.dao;

import com.henry.weixin.domain.db.PfPackDO;
import org.springframework.stereotype.Repository;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
@Repository
public class PfPackDao extends BaseDAO {

    public int insert(PfPackDO pfPackDO) throws Exception{
        if(pfPackDO==null) {
            return 0;
        }
        return sessionTemplate.insert("com.henry.weixin.dao.PfPackDao.insert",pfPackDO);
    }
}
