package com.rent.dao;

import com.rent.base.BaseDao;
import com.rent.entity.Region;
import com.rent.entity.Rent;

import java.util.List;

/**
 * 地区数据处理层
 * @author Exrickx
 */
public interface RegionDao extends BaseDao<Region,Integer> {

    /**
     * 获取子级
     * @param id
     * @return
     */
    List<Region> findByParentIdOrderByOrderAsc(Integer id);

    /**
     * 通过名称超找
     * @param name
     * @return
     */
    List<Region> findByNameLike(String name);
}
