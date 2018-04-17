package com.rent.serviceimpl;

import com.rent.dao.RegionDao;
import com.rent.dao.RentDao;
import com.rent.entity.Region;
import com.rent.service.RegionService;
import com.rent.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Override
    public RegionDao getRepository() {
        return regionDao;
    }

    @Override
    public List<Region> findByParentIdOrderByOrderAsc(Integer id) {
        return regionDao.findByParentIdOrderByOrderAsc(id);
    }
}
