package com.kxj.web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * @author kxj
 * @date 2020/4/1 22:38
 * @desc
 */
//@Component
public class MyCustomViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}
