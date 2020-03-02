package com.kxj.config;

import com.kxj.filter.TimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kxj
 * @date 2020/3/2 21:02
 * @desc
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        // 设置过滤器
        registration.setFilter(timeFilter);
        // 设置URL
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registration.setUrlPatterns(urls);
        return registration;
    }
}
