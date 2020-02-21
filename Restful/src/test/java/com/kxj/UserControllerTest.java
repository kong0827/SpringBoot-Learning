package com.kxj;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kxj
 * @date 2019/12/26 23:41
 * @Desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 查询
     * @throws Exception
     */
    @Test
    public void whenQuerySuccess() throws Exception {
        //发送get请求
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "tom")
                .param("age", "21")
                .param("sex","2")
                //请求的格式
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //请求的相应期待是200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //返回的长度是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 正则表达式只能输入数字
     * @throws Exception
     */
    @Test
    public void whenGenInfoSuccess1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 创建请求
     */
    @Test
    public void whenCreateSuccess() throws Exception {
        String content = "{\"username\": \"tom\", \"age\": 10}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * 日期类型处理，只传递时间戳
     */
    @Test
    public void whenDateSuccess() throws Exception {
        Date date = new Date();
        String content = "{\"username\": \"tom\",\"username\": \"tom\", \"age\": 10, \"birthday\":"+date.getTime()+"}";
        String reuslt = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    /**
     * 参数校验
     * @throws Exception
     */
    @Test
    public void whenJSR303Success() throws Exception {
        Date date = new Date();
        String content = "{\"id\": null,\"username\": \"tom\", \"age\": 10, \"birthday\":"+date.getTime()+"}";
        String reuslt = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    /**
     * 修改测试 put请求
     */
    @Test
    public void updateTest() throws Exception {
        Date date = new Date();
        String content = "{\"id\": 1,\"username\": \"tom\", \"age\": 10, \"birthday\":"+date.getTime()+"}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

}


