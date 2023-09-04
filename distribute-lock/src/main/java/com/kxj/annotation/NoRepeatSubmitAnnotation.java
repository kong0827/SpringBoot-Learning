package com.kxj.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2022/6/21 20:56
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmitAnnotation {

    /**
     * 间隔时间
     * @return
     */
    int interval() default 2000;

    /**
     * 时间单位 默认秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    /**
     * 提示信息
     * @return
     */
    String message() default "操作过于频繁，请稍后重试";
}
