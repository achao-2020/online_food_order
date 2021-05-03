package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Data
@ApiModel(value = "订单视图实体类", description = "用于显示订单的信息")
public class OrderVO extends BaseVO{
    private static final long serialVersionUID = 1443866181208698351L;
    @ApiModelProperty(value = "id(订单id)")
    private String id;
    @ApiModelProperty(value = "finish_time(订单完成时间)")
    private Date finishTime;
    @ApiModelProperty(value = "status(订单状态，status=1时候，表示订单准备，status=2配送中, status=3完成)")
    private Integer status;
    @ApiModelProperty(value = "customerId(订单关联顾客id)")
    private String customerId;
    @ApiModelProperty(value = "dishes(订单关联餐品列表)")
    private List<OrderRefDishesVO> dishes;
    @ApiModelProperty(value = "storeId(订单关联商店id)")
    private String storeId;
    @ApiModelProperty(value = "deliveredId(订单关联配送员id)")
    private String deliveredId;
    @ApiModelProperty(value = "longitude(配送经度)")
    private Double longitude;
    @ApiModelProperty(value = "latitude(配送纬度)")
    private Double latitude;
    @ApiModelProperty(value = "address(配送地址)")
    private String address;
}
