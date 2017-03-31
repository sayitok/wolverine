package com.henry.weixin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sanfen.yf on 2017/3/11.
 *
 * @author sanfen.yf
 * @date 2017/03/11
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqDesc {

    String command();

    String[] alias() default {};

    String url() default "";

    String[] paras() default {};
}
