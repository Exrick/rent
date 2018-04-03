package com.rent.dao;

import com.rent.base.BaseDao;
import com.rent.entity.Rent;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
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
    Page<Rent> findByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    /**
     * 分页获取用户发布信息
     * @param userId
     * @param pageable
     * @return
     */
    Page<Rent> findByUserIdOrderByCreateTimeDesc(Integer userId, Pageable pageable);
}
