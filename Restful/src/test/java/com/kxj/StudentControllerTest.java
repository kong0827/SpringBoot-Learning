package com.kxj;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kxj
 * @date 2020/2/13 17:47
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * @JsonView的验证
     * 简单视图（不带密码属性）
     * @throws Exception
     */
    @Test
    public void testQuery() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    /**
     * @JsonView的验证
     * 详细视图（配置带密码）
     * @throws Exception
     */
    @Test
    public void testQuery1() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/student2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    /**
     * 修改测试 put请求
     */
    @Test
    public void updateTest() throws Exception {
        /**
         * 日期加一天
         */
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        date = c.getTime();

        String content = "{\"id\": 1,\"username\": \"tom\", \"password\": null, \"birthday\":"+date.getTime()+"}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.put("/student/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    /**
     * 删除测试 delete请求
     */
    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testQuery2() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/student/time").param("time", new Date().getTime()+""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }


}
