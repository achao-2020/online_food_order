package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 */
@Data
@ApiModel(value = "订单关联餐品视图层")
public class OrderRefDishesVO extends BaseVO{
    @ApiModelProperty(value = "id(餐品id)")
    private String id;
    @ApiModelProperty(value = "storeId(关联商品id)")
    private String storeId;
    @ApiModelProperty(value = "name(餐品名称)")
    private String name;
    @ApiModelProperty(value = "price(价格)")
    private BigDecimal price;
    @ApiModelProperty(value = "photo(图片url地址)")
    private String photo;
    @ApiModelProperty(value = "number(数量)")
    private Integer number;
}
