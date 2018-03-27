package com.rent.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 全国地区信息
 * @author Exrickx
 */
@Data
@Entity
@Table(name = "t_region")
public class Region implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "唯一标识")
    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "地区id")
    private Integer regionId;

    @ApiModelProperty(value = "地区父id")
    private Integer regionParentId;

    @ApiModelProperty(value = "地区名")
    private String regionName;

    @ApiModelProperty(value = "类型 1省 2市 3区")
    private Integer regionType;

    @ApiModelProperty(value = "邮编")
    private String zipcode;

    @ApiModelProperty(value = "区号")
    private String quhao;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
