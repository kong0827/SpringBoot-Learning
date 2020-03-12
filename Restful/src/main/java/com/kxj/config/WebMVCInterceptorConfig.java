package com.kxj.config;

import com.kxj.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
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

    /**
     * 异步请求的配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 设置一个异步线程池
        // 默认使用SimpleAsyncTaskExecutor
        configurer.setTaskExecutor(new SimpleAsyncTaskExecutor());

        // 设置异步request等待被处理的超时时间
        // 默认的大小为10秒
        configurer.setDefaultTimeout(100);

        // 设置Callable任务的拦截器
        configurer.registerCallableInterceptors(new CallableProcessingInterceptor(){});

        // 设置Callable任务的带有延迟的拦截器
        configurer.registerDeferredResultInterceptors(new DeferredResultProcessingInterceptor() {});
    }
}
