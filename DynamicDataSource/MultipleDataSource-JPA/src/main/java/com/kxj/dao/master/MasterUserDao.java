package com.kxj.dao.master;

import com.kxj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xiangjin.kong
 * @date 2020/7/3 14:08
 * @desc
 */
public interface MasterUserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
