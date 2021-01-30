package com.kxj.repository;

import com.kxj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 17:22
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
