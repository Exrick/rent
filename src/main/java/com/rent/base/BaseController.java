package com.rent.base;

import com.rent.common.utils.PageUtil;
import com.rent.common.utils.ResultUtil;
import com.rent.vo.PageVo;
import com.rent.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrickx
 */
public abstract class BaseController<E, ID extends Serializable> {

    /**
     * 获取service
     * @return
     */
    @Autowired
    public abstract BaseService<E,ID> getService();

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过id获取")
    public Result<E> get(@PathVariable ID id){

        E entity = getService().get(id);
        return new ResultUtil<E>().setData(entity);
    }

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取全部数据")
    public Result<List<E>> getAll(){

        List<E> list = getService().getAll();
        return new ResultUtil<List<E>>().setData(list);
    }

    @RequestMapping(value = "/getByPage",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取")
    public Result<Page<E>> getByPage(@ModelAttribute PageVo page){

        Page<E> list = getService().findAll(PageUtil.initPage(page));
        return new ResultUtil<Page<E>>().setData(list);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public Result<E> save(@ModelAttribute E entity){

        E e = getService().save(entity);
        return new ResultUtil<E>().setData(e);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新数据")
    public Result<E> update(@ModelAttribute E entity){

        E e = getService().update(entity);
        return new ResultUtil<E>().setData(e);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除数据")
    public Result<Object> delAll(@ModelAttribute E entity){

        getService().delete(entity);
        return new ResultUtil<Object>().setSuccessMsg("删除数据成功");
    }

    @RequestMapping(value = "/delByIds",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@RequestParam ID[] ids){

        for(ID id:ids){
            getService().delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量删除数据成功");
    }
}
