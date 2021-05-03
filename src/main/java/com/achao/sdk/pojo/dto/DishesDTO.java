package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "餐品传输对象")
public class DishesDTO extends BaseDTO {
    @NotReflect
    private static final long serialVersionUID = -613499095057061095L;
    @ApiModelProperty(value = "id", notes = "id")
    private String id;
    @ApiModelProperty(value = "name", notes = "餐品名称")
    private String name;
    @ApiModelProperty(value = "description", notes = "餐品描述")
    private String description;
    @ApiModelProperty(value = "discount", notes = "餐品折扣")
    private Integer discount;
    @ApiModelProperty(value = "remain", notes = "餐品余量")
    private Integer remain;
    @ApiModelProperty(value = "photo", notes = "餐品样张url")
    private String photo;
    @ApiModelProperty(value = "price", notes = "餐品价格")
    private BigDecimal price;
    @ApiModelProperty(value = "storeId", notes = "关联商店id")
    private String storeId;
}
