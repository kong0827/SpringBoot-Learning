package com.kxj.annotation;

import java.lang.annotation.*;

/**
 * @author xiangjin.kong
 * @date 2022/3/23 11:47
 */
@Documented
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CompensationAnnotation {

    Class<?> exceptionType() default Exception.class;
}
