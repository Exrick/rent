package com.rent.dao;

import com.rent.base.BaseDao;
import com.rent.entity.Rent;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 房屋信息数据处理层
 * @author Exrickx
 */
public interface RentDao extends BaseDao<Rent,Integer> {

    /**
     * 分页获取已通过信息
     * @param status
     * @param pageable
     * @return
     */
    List<Rent> findByStatusIs(Integer status, Pageable pageable);
}
