package com.kxj.read;

import com.alibaba.excel.EasyExcel;
import com.kxj.dao.DemoDao;
import com.kxj.dao.IndexOrNameDemoDao;
import com.kxj.listener.DemoDataListener;
import com.kxj.listener.IndexOrNameDataListener;
import com.kxj.model.DemoData;
import com.kxj.model.IndexOrNameData;
import com.kxj.util.TestFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 13:58
 * @desc 最简单的读
 */
@Controller
public class ReadController {

    @Autowired
    DemoDao demoDao;

    @Autowired
    IndexOrNameDemoDao indexOrNameDemoDao;

    @GetMapping("simple-read")
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener(demoDao)).sheet().doRead();
    }

    @GetMapping("index-or-name-read")
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener(indexOrNameDemoDao)).sheet().doRead();
    }
}
