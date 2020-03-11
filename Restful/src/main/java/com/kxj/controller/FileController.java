package com.kxj.controller;

import com.kxj.entity.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author kxj
 * @date 2020/3/11 22:15
 * @desc
 */

@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        String filePath = "E:/githubResp/SpringBoot-Demo/Restful/src/main/resources";
        File localFile = new File(filePath,
                LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + ".txt");
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    /**
     * 文件下载
     */
    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(
                    new File("E:/githubResp/SpringBoot-Demo/Restful/src/main/resources/" + id + ".txt"));
            outputStream = response.getOutputStream();
            response.setContentType("application/x-download");

            /**
             * test.txt 下载下来的文件名
             */
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            /**
             * 把文件的输入流复制到输出流中
             * 即把文件写入到响应中
             */
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            outputStream.close();
            inputStream.close();
        }

    }
}
