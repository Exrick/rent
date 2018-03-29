package com.rent.entity;

import com.rent.base.BaseEntity;
import com.rent.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 房屋发布信息
 * @author Exrickx
 */
@Data
@Entity
@Table(name = "t_rent")
public class Rent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出租类型 0整租 1合租")
    private Integer type;

    @ApiModelProperty(value = "小区名")
    private String houseName;

    @ApiModelProperty(value = "房屋面积 可小数点后2位")
    private BigDecimal houseArea;

    @ApiModelProperty(value = "室数量")
    private Integer roomNum;

    @ApiModelProperty(value = "厅数量")
    private Integer hallNum;

    @ApiModelProperty(value = "卫数量")
    private Integer toiletNum;

    @ApiModelProperty(value = "楼层")
    private Integer floor;

    @ApiModelProperty(value = "总楼层")
    private Integer floorTotal;

    @ApiModelProperty(value = "省")
    private Integer province;

    @ApiModelProperty(value = "市")
    private Integer city;

    @ApiModelProperty(value = "县")
    private Integer area;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "租金  可小数点后2位 -1面议")
    private BigDecimal price;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "qq")
    private String qq;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "图片1 默认封面 必填项")
    private String image1;

    @ApiModelProperty(value = "图片2")
    private String image2;

    @ApiModelProperty(value = "图片3")
    private String image3;

    @ApiModelProperty(value = "发布用户id")
    private Integer userId;

    @ApiModelProperty(value = "状态 默认0待审核 1审核通过发布 2审核不通过 3下架")
    private Integer status = CommonConstant.STATUS_RENT_DEFAULT;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;
}
