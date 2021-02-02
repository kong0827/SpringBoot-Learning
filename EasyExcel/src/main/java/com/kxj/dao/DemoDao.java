package com.kxj.dao;

import com.kxj.model.DemoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiangjin.kong
 * @date 2021/2/2 11:50
 */
@Repository
public interface DemoDao extends JpaRepository<DemoData, Long> {
}
