package com.kxj.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author xiangjin.kong
 * @date 2021/3/23 19:33
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfiguration {
    private Integer port;
    private String name;
    private Boolean success;
    private User user;
    private List<String> list;
    private List<User> users;
    private Map<String, String> map;
    private Map<String, List<String>> mapList;
    private Map<String, User> userMap;
    private List<Map<String, String>> listMap;
    private List<Map<String, User>> userList;


}
