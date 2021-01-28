package com.kxj.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2021/1/27 17:55
 * <p>
 *     限流注解，添加了{@link AliasFor} 必须通过{@link AnnotationUtils} 获取，才会生效
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    long DEFAULT_REQUEST = 10;

    /**
     * 最大请求数
     */
    @AliasFor("max")
    long value() default DEFAULT_REQUEST;

    /**
     * 最大请求数
     */
    @AliasFor("value")
    long max() default DEFAULT_REQUEST;

    /**
     * 限流key
     */
    String key() default "";

    /**
     * 超时时长, 默认一分钟
     */
    long timeout() default 1;

    /**
     * 超时时间单位，默认 分钟
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
