package com.zhiweicloud.guest.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DisplayValue.java
 * 展示的属性值
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/30 11:51
 * @author tiecheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayValue {

    /**
     * key
     * @return
     */
    String key();

    /**
     * state
     * @return
     */
    String value();

}
