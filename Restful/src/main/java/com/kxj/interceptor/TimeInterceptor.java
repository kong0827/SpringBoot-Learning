package com.kxj.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author kxj
 * @date 2020/3/2 21:14
 * @desc
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用。
     * @param request
     * @param response
     * @param handler   控制器方法的声明
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute("startTime", startTime);
//        System.out.println("拦截的方法" + ((HandlerMethod)handler).getMethod().getName());
        //
        return true;
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long millis = duration.toMillis();
        System.out.println("Interceptor耗时为：" + millis + "毫秒");
    }

    /**
     * 控制器方法执行后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor执行结束");
        System.out.println(ex);

    }
}
