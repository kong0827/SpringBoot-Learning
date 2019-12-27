package com.kxj.utils;

/**
 * @ClassName TokenDetailImpl
 * @Description TODO
 * @Author kongxiangjin
 * @Date 2019/12/26 17:28
 * @Version 1.0
 **/
public class TokenDetailImpl implements TokenDetail {

    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
