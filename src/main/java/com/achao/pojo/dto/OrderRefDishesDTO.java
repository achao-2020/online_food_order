package com.achao.pojo.dto;


import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单关联传输类
 * @author achao
 */
@Data
@ApiModel(value = "订单关联传输类")
public class OrderRefDishesDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -7926174074392265371L;
    @ApiModelProperty(value = "dishesId", notes = "餐品id")
    private String dishesId;
    @ApiModelProperty(value = "storeId", notes = "商品id")
    private String storeId;
}
