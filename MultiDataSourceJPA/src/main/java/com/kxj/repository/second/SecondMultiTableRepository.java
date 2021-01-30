package com.kxj.repository.second;

import com.kxj.entity.second.SecondMultiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 14:48
 */
@Repository
public interface SecondMultiTableRepository extends JpaRepository<SecondMultiTable, Long> {
}
