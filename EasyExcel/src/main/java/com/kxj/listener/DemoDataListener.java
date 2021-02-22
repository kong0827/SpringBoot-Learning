package com.kxj.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.kxj.dao.DemoDao;
import com.kxj.model.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 11:44
 */
@Slf4j
// DemoDataListener 不能被Spring管理，要每次读取Excel的时候去new，然后里面用到spring可以构造方法传入
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    // 每隔5条存进数据库, 实际中可以上千，然后清理list，方便内存回收
    private static final int BATCH_COUNT = 5;
    List<DemoData> list = new ArrayList<>();

    private DemoDao demoDao;

    public DemoDataListener() {
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param demoDao
     */
    public DemoDataListener(DemoDao demoDao) {
        this.demoDao = demoDao;
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param demoData
     * @param analysisContext
     */
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        log.info("解析一条数据：{}", JSON.toJSONString(demoData));
        list.add(demoData);
        if (list.size() > BATCH_COUNT) {
            saveData();
            list.clear();
        }

    }

    /**
     * 所有数据解析完成调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成");
    }

    /**
     * 保存数据库
     */
    private void saveData() {
        log.info("{}条 数据，开始存储数据库", list.size());
        demoDao.saveAll(list);
        log.info("存储数据库成功");
    }
}
