package com.rent.serviceimpl;

import cn.hutool.core.util.StrUtil;
import com.rent.common.constant.CommonConstant;
import com.rent.dao.RentDao;
import com.rent.entity.Rent;
import com.rent.vo.SearchVo;
import com.rent.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Page<Rent> findByStatus(Integer status, Pageable pageable) {
        return rentDao.findByStatusOrderByCreateTimeDesc(status,pageable);
    }

    @Override
    public Page<Rent> findByUserIdOrderByCreateTimeDesc(Integer userId, Pageable pageable) {
        return rentDao.findByUserIdOrderByCreateTimeDesc(userId,pageable);
    }

    @Override
    public Page<Rent> searchRent(SearchVo search, Pageable pageable) {
        return rentDao.findAll(new Specification<Rent>() {
            @Override
            public Predicate toPredicate(Root<Rent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path<Integer> typeField = root.get("type");
                Path<String> houseNameField = root.get("houseName");
                Path<Integer> roomNumField = root.get("roomNum");
                Path<Integer> provinceField=root.get("province");
                Path<Integer> cityField=root.get("city");
                Path<Integer> areaField=root.get("area");
                Path<String> addressField=root.get("address");
                Path<BigDecimal> priceField=root.get("price");
                Path<String> titleField=root.get("title");
                Path<String> descriptionField=root.get("description");
                Path<String> statusField=root.get("status");

                List<Predicate> list = new ArrayList<Predicate>();

                //已发布信息
                list.add(cb.equal(statusField, CommonConstant.STATUS_RENT_POST));

                //类型
                if(search.getType()!=null){
                    list.add(cb.equal(typeField,search.getType()));
                }
                //室
                if(search.getRoomNum()!=null){
                    list.add(cb.equal(roomNumField,search.getRoomNum()));
                }
                //省
                if(search.getProvince()!=null){
                    list.add(cb.equal(provinceField,search.getProvince()));
                }
                //市
                if(search.getCity()!=null){
                    list.add(cb.equal(cityField,search.getCity()));
                }
                //县
                if(search.getArea()!=null){
                    list.add(cb.equal(areaField,search.getArea()));
                }

                //价格区间
                if(search.getPriceGt()!=null&&search.getPriceLe()!=null){
                    list.add(cb.between(priceField,search.getPriceGt(),search.getPriceLe()));
                }else if(search.getPriceGt()!=null&&search.getPriceLe()==null){
                    list.add(cb.greaterThanOrEqualTo(priceField,search.getPriceGt()));
                }else if(search.getPriceGt()==null&&search.getPriceLe()!=null){
                    list.add(cb.lessThanOrEqualTo(priceField,search.getPriceLe()));
                }

                //模糊搜索
                if(StrUtil.isNotBlank(search.getKey())){
                    Predicate p1 = cb.like(houseNameField,'%'+search.getKey()+'%');
                    Predicate p2 = cb.like(addressField,'%'+search.getKey()+'%');
                    Predicate p3 = cb.like(titleField,'%'+search.getKey()+'%');
                    Predicate p4 = cb.like(descriptionField,'%'+search.getKey()+'%');

                    list.add(cb.or(p1,p2,p3,p4));
                }

                Predicate[] arr = new Predicate[list.size()];
                query.where(list.toArray(arr));
                return null;
            }
        },pageable);
    }
}
