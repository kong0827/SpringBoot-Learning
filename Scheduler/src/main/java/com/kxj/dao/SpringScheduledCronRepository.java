package com.kxj.dao;

import com.kxj.entity.SpringScheduledCron;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiangjin.kong
 * @date 2020/10/23 16:18
 */
public interface SpringScheduledCronRepository extends JpaRepository<SpringScheduledCron, Integer> {

    SpringScheduledCron findByCronKey(String cronKey);

}
