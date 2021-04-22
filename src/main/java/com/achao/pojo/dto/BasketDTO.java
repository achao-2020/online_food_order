package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "购物篮传输对象")
public class BasketDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -6226127695458083251L;

    @ApiModelProperty(value = "id", notes = "id")
    private String id;

    @ApiModelProperty(value = "storeId", notes = "餐品关联商店id")
    private String storeId;

    @ApiModelProperty(value = "storeName", notes = "餐品关联商店名称")
    private String storeName;

    @ApiModelProperty(value = "dishesId", notes = "餐品id")
    private String dishesId;

    @ApiModelProperty(value = "dishesName", notes = "餐品名称")
    private String dishesName;

    @ApiModelProperty(value = "description", notes = "餐品描述")
    private String description;

    @ApiModelProperty(value = "discount", notes = "餐品折扣")
    private Integer discount;

    @ApiModelProperty(value = "photo", notes = "餐品样张url")
    private String photo;

    @ApiModelProperty(value = "count", notes = "餐品数量")
    private Integer count;
}
