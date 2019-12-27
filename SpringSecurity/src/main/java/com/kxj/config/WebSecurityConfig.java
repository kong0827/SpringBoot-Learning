package com.kxj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.security.PermitAll;

/**
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/12/26 17:08
 * @Version 1.0
 **/
@Configuration              // 声明为配置类
@EnableWebSecurity          // 启用Spring Security Web安全的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 允许所有请求通过
            .anyRequest().permitAll()
            .and()
            .csrf()
            // 禁用Spring Security自带的跨域处理
            .disable()
            // 定制我们自己的session策略
            .sessionManagement()
            // 调整为让Spring Security不管理和创建Session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
