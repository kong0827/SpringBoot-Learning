package com.kxj.write;

import com.alibaba.excel.EasyExcel;
import com.kxj.dao.DemoDao;
import com.kxj.model.DemoData;
import com.kxj.util.TestFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 15:49
 */
@RestController
public class WriteController {

    @Autowired
    DemoDao demoDao;

    @GetMapping("simple-write")
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(demoDao.findAll());
    }

}
