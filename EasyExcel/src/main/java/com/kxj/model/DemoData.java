package com.kxj.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 11:43
 */
@Data
@Entity
@Table
public class DemoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelIgnore
    private Long id;

    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date data;
    @ExcelProperty("数字标题")
    private Double doubleData;
}
