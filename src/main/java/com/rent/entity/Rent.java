package com.rent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.base.BaseEntity;
import com.rent.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

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

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市")
    private Integer city;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "县")
    private Integer area;

    @ApiModelProperty(value = "县名称")
    private String areaName;

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

    @ApiModelProperty(value = "图片4")
    private String image4;

    @ApiModelProperty(value = "图片5")
    private String image5;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "发布用户id")
    private Integer userId;

    @ApiModelProperty(value = "状态 默认0待审核 1审核通过发布 2审核不通过 3下架")
    private Integer status = CommonConstant.STATUS_RENT_DEFAULT;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "驳回理由")
    private String backReason;

    @ApiModelProperty(value = "成交状态 默认0未成交 1成交")
    private Integer dealStatus = CommonConstant.STATUS_RENT_DEFAULT;

    @ApiModelProperty(value = "成交金额")
    private BigDecimal dealPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "成交时间 接收格式yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    @ApiModelProperty(value = "浏览人数")
    private Integer viewCount;

    @Transient
    @ApiModelProperty(value = "发布用户名")
    private String username;
}
