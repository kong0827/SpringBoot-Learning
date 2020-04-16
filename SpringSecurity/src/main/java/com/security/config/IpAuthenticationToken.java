package com.security.config;/**
 * @author xiangjin.kong
 * @date 2020/1/17 14:19
 * @desc
 */

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName IpAuthenticationToken
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2020/1/17 14:19
 * @Version 1.0
 **/
public class IpAuthenticationToken extends AbstractAuthenticationToken {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IpAuthenticationToken(String ip) {
        super(null);
        this.ip = ip;
        super.setAuthenticated(false);
    }

    public IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.ip;
    }
}
