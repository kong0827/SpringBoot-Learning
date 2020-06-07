package com.kxj.mybatis.bean;

/**
 * @author tommy
 * @title: Comment
 * @projectName coderead-mybatis
 * @description: 评论实体
 * @date 2020/5/2910:21 PM
 */
public class Comment implements java.io.Serializable {

    private String id;
    private Integer blogId;
    private String body;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}
