package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author kxj
 * @date 2020/2/20 22:43
 * @Desc
 */
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(secure = false)
//@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * get请求，，少量参数验证
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/teacher")
                .param("username", "x")
                .param("id", "120")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * 实体类数据校验
     * @throws Exception
     */
    @Test
    public void objectRequestTest() throws Exception {
        String content = "{\"username\": \"\",\"password\": \"\", \"id\": 11111111}";
        mockMvc.perform(post("/teacher")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * get请求对象接收参数验证
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/teacher1")
                .param("username", "")
                .param("id", "120")
                .param("password", "120")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * 分组参数验证
     */
    @Test
    public void testGroup() throws Exception {
        String content = "{\"username\": \"xx\",\"password\": \"xx\", \"id\": \"\"}";
        mockMvc.perform(post("/teachers")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
