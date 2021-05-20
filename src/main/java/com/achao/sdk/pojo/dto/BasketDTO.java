package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "购物篮传输对象")
public class BasketDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -6226127695458083251L;

    @ApiModelProperty(value = "id", notes = "id")
    private String id;

    @ApiModelProperty(value = "customerId(顾客id)", notes = "关联顾客id")
    private String customerId;

    @ApiModelProperty(value = "storeId", notes = "餐品关联商店id")
    private String storeId;

    @ApiModelProperty(value = "dishesId", notes = "餐品id")
    private String dishesId;

    @ApiModelProperty(value = "dishesName", notes = "餐品名称")
    private String dishesName;

    @ApiModelProperty(value = "price(餐品单价)", notes = "餐品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "description", notes = "餐品描述")
    private String description;

    @ApiModelProperty(value = "discount", notes = "餐品折扣")
    private Integer discount;

    @ApiModelProperty(value = "photo", notes = "餐品样张url")
    private String photo;

    @ApiModelProperty(value = "count", notes = "餐品数量")
    private Integer count;
}
