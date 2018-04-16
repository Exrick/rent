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

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "地区代码")
    private Integer code;

    @ApiModelProperty(value = "排序")
    private Integer order;
}
