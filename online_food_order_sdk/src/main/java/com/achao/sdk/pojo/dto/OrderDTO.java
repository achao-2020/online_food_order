package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author achao
 */
@Data
@ApiModel(value = "订单传输对象")
public class OrderDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 8045024219986468323L;
    @ApiModelProperty(value = "id", notes = "id")
    private String id;
    @ApiModelProperty(value = "storeId(关联商品id)")
    private String storeId;
    @ApiModelProperty(value = "customerId(订单关联顾客id)")
    private String customerId;
    @ApiModelProperty(value = "dishes(餐品id,一个订单可以对应多个餐品)")
    private List<DishesOrderDTO> dishes;
    @ApiModelProperty(value = "contact(配送联系方式)")
    private String contact;
    @ApiModelProperty(value = "longitude(配送经度)")
    private Double longitude;
    @ApiModelProperty(value = "latitude(配送纬度)")
    private Double latitude;
    @ApiModelProperty(value = "address(配送地址)")
    private String address;
}
