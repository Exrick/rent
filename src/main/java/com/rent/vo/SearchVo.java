package com.rent.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Exrickx
 */
@Data
public class SearchVo implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出租类型 0整租 1合租")
    private Integer type;

    @ApiModelProperty(value = "室数量")
    private Integer roomNum;

    @ApiModelProperty(value = "省")
    private Integer province;

    @ApiModelProperty(value = "市")
    private Integer city;

    @ApiModelProperty(value = "县")
    private Integer area;

    @ApiModelProperty(value = "租金  可小数点后2位 -1面议")
    private BigDecimal price;

    @ApiModelProperty(value = "搜索关键词")
    private String key;

    @ApiModelProperty(value = "价格搜索起始价格")
    private BigDecimal priceGt;

    @ApiModelProperty(value = "价格搜索终止价格")
    private BigDecimal priceLe;
}
