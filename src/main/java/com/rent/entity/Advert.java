package com.rent.entity;

import com.rent.base.BaseEntity;
import com.rent.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 广告实体类
 * @author Exrickx
 */
@Data
@Entity
@Table(name = "t_advert")
public class Advert extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "关联跳转发布信息id")
    private Integer relateId;

    @ApiModelProperty(value = "跳转链接")
    private String redirectUrl;

    @ApiModelProperty(value = "图片链接")
    private String picUrl;

    @ApiModelProperty(value = "广告类型 0站内 1外链")
    private Integer type;

    @ApiModelProperty(value = "状态 默认0正常 -1下架")
    private Integer status = CommonConstant.STATUS_NORMAL;
}
