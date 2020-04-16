package com.security.filter;/**
 * @author xiangjin.kong
 * @date 2020/1/17 14:23
 * @desc
 */

import com.security.config.IpAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName IpAuthenticationProcessingFilter
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2020/1/17 14:23
 * @Version 1.0
 **/
public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    protected IpAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/ipVerify"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String host = httpServletRequest.getRemoteHost();
        return getAuthenticationManager().authenticate(new IpAuthenticationToken(host));
    }
}
