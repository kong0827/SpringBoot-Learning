package com.kxj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author kxj
 * @date 2020/3/11 21:57
 * @desc
 */

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false)
public class FileUploadAndDownloadTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebMVCInterceptorConfig webMVCInterceptorConfig;

    /**
     * 测试文件上传，上传文件字符串
     *
     * @throws Exception
     */
    @Test
    public void testUpload() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "multipart/form-data", "hello world".getBytes("UTF-8"));

        String content = mockMvc.perform(MockMvcRequestBuilders.multipart("/file").file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content:" + content);
    }
}
