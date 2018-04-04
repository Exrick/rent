package com.rent.service;

import com.rent.base.BaseService;
import com.rent.entity.Region;
import com.rent.entity.Rent;

import java.util.List;

/**
 * 地区信息接口
 * @author Exrickx
 */
public interface RegionService extends BaseService<Region,Integer> {

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
    Region findByRegionId(Integer regionId);
}
