package com.achao.sdk.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author achao
 */
@Data
@ApiModel(value = "订单餐品内置传输对象")
public class DishesOrderDTO extends BaseDTO{
    @ApiModelProperty(value = "dishesId(餐品id)")
    private String dishesId;
    @ApiModelProperty(value = "count(餐品数量)")
    private Integer number;
}
