package com.henry.weixin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanfen.yf on 2017/3/3.
 *
 * @author sanfen.yf
 * @date 2017/03/03
 */
public class LoggerFilter implements Filter {

    protected final static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.warn("filter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        logger.warn("Req URI={}",request.getRequestURI());
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        logger.warn("filter destroy");
    }
}