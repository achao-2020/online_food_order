package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "订单传输对象")
public class OrderDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 8045024219986468323L;
    @ApiModelProperty(value = "id", notes = "id")
    private String id;
    @ApiModelProperty(value = "customerId", notes = "订单关联顾客id")
    private String customerId;
    @ApiModelProperty(value = "storeId", notes = "订单关联商店id")
    private String storeId;
    @ApiModelProperty(value = "dishesId", notes = "餐品id")
    private String dishesId;
    @ApiModelProperty(value = "deliveredId", notes = "订单关联配送员id")
    private String deliveredId;
}
