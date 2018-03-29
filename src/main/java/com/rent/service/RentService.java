package com.rent.service;

import com.rent.base.BaseService;
import com.rent.entity.Rent;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 房屋信息接口
 * @author Exrickx
 */
public interface RentService extends BaseService<Rent,Integer> {

    /**
     * 分页获取已通过信息
     * @param status
     * @param pageable
     * @return
     */
    List<Rent> findByStatusIs(Integer status, Pageable pageable);
}
