package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import com.achao.pojo.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
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
    @ApiModelProperty(value = "storeId", notes = "关联商品id")
    private String storeId;
    @ApiModelProperty(value = "customerId", notes = "订单关联顾客id")
    private String customerId;
    @ApiModelProperty(value = "dishIds", notes = "餐品id,一个订单可以对应多个餐品")
    private List<String> dishIds;
    @ApiModelProperty(value = "deliveredId", notes = "订单关联配送员id")
    private String deliveredId;
}
