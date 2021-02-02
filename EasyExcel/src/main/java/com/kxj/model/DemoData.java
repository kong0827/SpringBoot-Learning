package com.kxj.model;

import com.alibaba.excel.annotation.ExcelIgnore;
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

    private String string;
    private Date data;
    private Double doubleData;
}
