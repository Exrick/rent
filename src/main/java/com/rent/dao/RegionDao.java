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
     * 通过类型获取
     * @param type
     * @return
     */
    List<Region> findByRegionType(Integer type);

    /**
     * 获取子级
     * @param id
     * @return
     */
    List<Region> findByRegionParentId(Integer id);

    /**
     * 通过regionId获取
     * @param regionId
     * @return
     */
    List<Region> findByRegionId(Integer regionId);
}
