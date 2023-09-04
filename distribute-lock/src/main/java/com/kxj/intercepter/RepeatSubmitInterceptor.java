package com.kxj.intercepter;

import com.kxj.annotation.NoRepeatSubmitAnnotation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author xiangjin.kong
 * @date 2022/6/21 21:00
 */
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            NoRepeatSubmitAnnotation annotation = method.getAnnotation(NoRepeatSubmitAnnotation.class);
            if (annotation != null) {
                this.isRepeatSubmit(request, annotation);
            }
        }
        return true;
    }

    /**
     * 判断是否是重复提交
     * @param request
     * @param annotation
     * @return
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, NoRepeatSubmitAnnotation annotation);

}
