package com.kxj.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author kxj
 * @date 2020/3/1 0:01
 * @desc
 */
//@Component
public class TimeFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LocalDateTime startTime = LocalDateTime.now();
        filterChain.doFilter(servletRequest, servletResponse);
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long millis = duration.toMillis();
        System.out.println("耗时为：" + millis + "毫秒");

    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁");
    }
}
