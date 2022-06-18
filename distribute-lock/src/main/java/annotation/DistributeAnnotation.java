package annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2022/6/18 14:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = ElementType.METHOD)
public @interface DistributeAnnotation {

    /**
     * 锁前缀
     * @return
     */
    String lockPrefix() default "";

    /**
     * 锁后缀
     * @return
     */
    String lockSuffix() default "";

    /**
     * 需要锁的参数
     * @return
     */
    String param() default "";

    /**
     * 需要锁的参数位置 默认第一个
     * @return
     */
    int argNum() default 0;

    /**
     * 锁尝试时间
     * @return
     */
    long waitTime() default 5;

    /**
     * 锁持有时间超过自动释放
     * @return
     */
    long leaseTime() default 60;

    /**
     * 时间单位 默认秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
