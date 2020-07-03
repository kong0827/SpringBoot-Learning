package com.kxj.dao.salve;

import com.kxj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/7/3 14:08
 * @desc
 */
public interface SalveUserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
