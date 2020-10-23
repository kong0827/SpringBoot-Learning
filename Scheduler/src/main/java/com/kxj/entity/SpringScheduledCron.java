package com.kxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author xiangjin.kong
 * @date 2020/10/23 15:59
 */
@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spring_scheduled_cron")
public class SpringScheduledCron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cron_id")
    private Integer cronId;

    @Column(name = "cron_key", unique = true)
    private String cronKey;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "task_explain")
    private String taskExplain;

    @Column(name = "status")
    private Integer status;

}
