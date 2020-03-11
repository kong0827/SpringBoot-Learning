package com.kxj.config;

import com.kxj.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kxj
 * @date 2020/3/2 21:37
 * @desc
 */
@Configuration
public class WebMVCInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     *
     * @param registry 拦截器的注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }
}
