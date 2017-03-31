package com.henry.weixin.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sanfen.yf on 2017/3/8.
 *
 * @author sanfen.yf
 * @date 2017/03/08
 */
public class BaseDAO {
    @Autowired
    protected SqlSessionTemplate sessionTemplate;
}
