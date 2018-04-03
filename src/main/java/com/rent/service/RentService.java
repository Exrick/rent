package com.rent.service;

import com.rent.base.BaseService;
import com.rent.entity.Rent;
import com.rent.vo.SearchVo;
import org.springframework.data.domain.Page;
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
    Page<Rent> findByStatus(Integer status, Pageable pageable);

    /**
     * 分页获取用户发布信息
     * @param userId
     * @param pageable
     * @return
     */
    Page<Rent> findByUserIdOrderByCreateTimeDesc(Integer userId, Pageable pageable);

    /**
     * 搜索接口
     * @param search
     * @param pageable
     * @return
     */
    Page<Rent> searchRent(SearchVo search, Pageable pageable);
}
