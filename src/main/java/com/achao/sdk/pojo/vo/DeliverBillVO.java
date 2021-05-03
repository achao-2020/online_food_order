package com.achao.sdk.pojo.vo;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 */
@Data
@ApiModel(value = "配送员账单视图层对象")
public class DeliverBillVO extends BaseVO {
    @NotReflect
    private static final long serialVersionUID = -6167850533184518139L;

    @ApiModelProperty(value = "deliverId", notes = "配送员id")
    private String deliverId;

    @ApiModelProperty(value = "deliverName", notes = "配送员姓名")
    private String deliverName;

    @ApiModelProperty(value = "orderId", notes = "order_id")
    private String orderId;

    @ApiModelProperty(value = "income", notes = "配送员收入")
    private BigDecimal income;

    @ApiModelProperty(value = "serviceFee", notes = "平台服务费（百分之零点五）")
    private BigDecimal serviceFee;
}
