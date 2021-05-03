package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "餐品视图层对象")
public class DishesVO extends BaseVO{
    @ApiModelProperty(value = "name", notes = "餐品名称")
    private String name;
    @ApiModelProperty(value = "description", notes = "餐品描述")
    private String description;
    @ApiModelProperty(value = "price", notes = "餐品价格")
    private BigDecimal price;
    @ApiModelProperty(value = "discount", notes = "餐品折扣")
    private Integer discount;
    @ApiModelProperty(value = "remain", notes = "剩余数量")
    private Integer remain;
    @ApiModelProperty(value = "storeId", notes = "关联商店id")
    private String storeId;
    @ApiModelProperty(value = "photo", notes = "餐品样张url地址")
    private String photo;
}
