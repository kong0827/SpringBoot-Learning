package com.kxj.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kxj
 * @date 2020/4/1 23:39
 * @desc
 */
@Configuration
public class CustomConverterConfig implements WebMvcConfigurer {

    @Autowired
    private DateConverter dateConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConverter);
    }
}
