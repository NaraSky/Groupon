package com.lb.types.annotation;

import java.lang.annotation.*;

/**
 * 动态配置注解
 * 格式：配置键:默认值（必填）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    /**
     * 配置格式：键值对（示例："isSwitch:1"）
     */
    String value() default "";
}