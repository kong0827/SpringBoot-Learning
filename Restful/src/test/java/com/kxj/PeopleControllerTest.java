package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author kxj
 * @date 2020/2/21 22:21
 * @desc
 */
@RunWith(SpringRunner.class)
@WebMvcTest(secure = false)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 组序列测试
     * @throws Exception
     */
    @Test
    public void requestTest1() throws Exception {
        String content = "{\"name\": \"xx\",\"id\": \"1\"}";
        mockMvc.perform(post("/people")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    /**
     * 组序列测试
     * @throws Exception
     */
    @Test
    public void requestTest2() throws Exception {
        String content = "[{\"name\": \"xx\",\"id\": \"x\"},{\"name\": \"xxxx\",\"id\": \"\"}]";
        mockMvc.perform(post("/peoples")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
