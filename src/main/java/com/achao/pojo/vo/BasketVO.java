package com.achao.pojo.vo;

import com.achao.annoation.NotReflect;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "购物篮视图层对象")
public class BasketVO extends BaseVO {
    @NotReflect
    private static final long serialVersionUID = -4227989884467096514L;

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
