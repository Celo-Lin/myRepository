package com.juan.adx.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juan.adx.common.enums.BusinessTypeEnum;

/**
 * 自定义操作日志记录注解
 * 
 * @author wangluming
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块 
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;


    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
