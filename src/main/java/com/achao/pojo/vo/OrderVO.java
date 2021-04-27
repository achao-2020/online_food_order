package com.achao.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "订单视图实体类", description = "用于显示订单的信息")
public class OrderVO extends BaseVO{
    private static final long serialVersionUID = 1443866181208698351L;
    @ApiModelProperty(value = "id", notes = "订单id")
    private String id;
    @ApiModelProperty(value = "finish_time", notes = "订单完成时间")
    private Date finishTime;
    @ApiModelProperty(value = "status", notes = "订单状态，status=1时候，表示订单准备，status=2配送中, status=3完成")
    private Integer status;
    @ApiModelProperty(value = "customerId", notes = "订单关联顾客id")
    private String customerId;
    @ApiModelProperty(value = "dishesId", notes = "餐品id")
    private String dishesId;
    @ApiModelProperty(value = "storeId", notes = "订单关联商店id")
    private String storeId;
    @ApiModelProperty(value = "deliveredId", notes = "订单关联配送员id")
    private String deliveredId;
}
