package com.rent.serviceimpl;

import com.rent.base.BaseDao;
import com.rent.dao.RentDao;
import com.rent.entity.Rent;
import com.rent.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Exrickx
 */
@Slf4j
@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentDao rentDao;

    @Override
    public RentDao getRepository() {
        return rentDao;
    }
}
