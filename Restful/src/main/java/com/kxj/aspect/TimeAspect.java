package com.kxj.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author kxj
 * @date 2020/3/10 21:39
 * @desc
 */

@Aspect
@Component
public class TimeAspect {

    /**
     * 声明切入点
     * 第一个 "*", 代表任何返回值
     * 第二个 "*"，代表此类中的任何方法
     */
    @Around(value = "execution(* com.kxj.controller.UserController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("time aspect start");
        LocalDateTime startTime = LocalDateTime.now();
        Object[] args = joinPoint.getArgs();
        Arrays.asList(args).stream().forEach(arg -> {
            System.out.println("请求参数为：" + arg);
        });

        Object object = joinPoint.proceed();

        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("time aspect end");
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("耗时：" + duration.minus(duration));
        return object;
    }

}
