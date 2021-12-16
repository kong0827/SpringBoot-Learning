package com.kxj.repository;

import com.kxj.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiangjin.kong
 * @date 2021/12/16 11:42
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
