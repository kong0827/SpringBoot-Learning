package com.kxj.intercepter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kxj.annotation.NoRepeatSubmitAnnotation;
import com.kxj.wrapper.RepeatedlyRequestWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangjin.kong
 * @date 2022/6/21 21:05
 * @desc 相同URL则视为重复
 */
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";
    public final static String REPEAT_SUBMIT_KEY = "REPEAT_SUBMIT_KEY";

    private String header = "Authorization";

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, NoRepeatSubmitAnnotation annotation) {
        String nowParams = "";

        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            try {
                nowParams = repeatedlyRequest.getReader().readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isEmpty(nowParams)) {
            try {
                nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        Map<String, Object> nowDataMap = new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
        return false;
    }
}
