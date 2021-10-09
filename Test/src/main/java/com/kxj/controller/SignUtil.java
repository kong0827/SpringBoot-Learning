package com.kxj.controller;

/**
 * @author xiangjin.kong
 * @date 2021/9/14 11:41
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


/**
 *
 */
public class SignUtil {

    /**
     * 获取 签名信息
     * <p>
     * 签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名
     */

    public static String getSign(Map params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);//排序

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(params.get(key)).append("&");//拼接字符串
        }

        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'

        String secret = "8a089a02e7a748cc88c29dcf47630dba";//密钥，自己修改
        System.out.println(linkString + secret);
        String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5

        return sign;
    }


    public static void main(String[] args) {
        Map params = new HashMap();

        params.put("sapcode", "0161000500");
        params.put("inventoryType", " ecpInventory");
        params.put("fromDate", "2020-10-29 14:59:00");
        params.put("toDate", "2020-10-29 16:00:00");
        params.put("key", "8cceba477e3542ea918471f076be1f9a");

        String sign = SignUtil.getSign(params);
        System.out.println(sign);
    }

}

