package com.rent.serviceimpl;

import com.rent.dao.AdvertDao;
import com.rent.service.AdvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告接口实现
 * @author Exrickx
 */
@Slf4j
@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertDao advertDao;

    @Override
    public AdvertDao getRepository() {
        return advertDao;
    }

}
