package com.kxj.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.kxj.convert.CustomConvert;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 15:12
 */
@Data
@Entity
@Table
public class IndexOrNameData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelIgnore
    private Long id;

    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 2, converter = CustomConvert.class)
    private Double doubleData;

    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;
}
