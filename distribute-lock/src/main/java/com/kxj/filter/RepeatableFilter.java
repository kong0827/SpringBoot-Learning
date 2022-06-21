package com.kxj.filter;
 
import com.kxj.wrapper.RepeatedlyRequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
 
/**
 * 过滤器（重写request）
 */
@Component
public class RepeatableFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        //将原生的request变为可重复读取inputStream的request
        if (request instanceof HttpServletRequest
                && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }
}