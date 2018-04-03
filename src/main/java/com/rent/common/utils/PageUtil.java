package com.rent.common.utils;

import cn.hutool.core.util.StrUtil;
import com.rent.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author Exrickx
 */
public class PageUtil {

    public static Pageable initPage(PageVo page){

        Pageable pageable=null;
        int pageNumber=page.getPageNumber();
        int pageSize=page.getPageSize();
        String sort=page.getSort();
        String order=page.getOrder();

        if(pageNumber<1){
            pageNumber=1;
        }
        if(pageSize<1){
            pageSize=10;
        }
        if(StrUtil.isNotBlank(sort)) {
            Sort.Direction d;
            if(StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            }else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d,sort);
            pageable = new PageRequest(pageNumber-1, pageSize,s);
        }else {
            pageable = new PageRequest(pageNumber-1, pageSize);
        }
        return pageable;
    }
}
