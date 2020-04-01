package com.kxj.web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author kxj
 * @date 2020/4/1 23:33
 * @desc
 */
@Component
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        return null;
    }
}
