package com.achao.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 */
@Data
@ApiModel(value = "账单商店视图层对象")
public class StoreBillVO extends BaseVO{

    @ApiModelProperty(name = "商店id")
    private String storeId;

    @ApiModelProperty(name = "商店名称")
    private String storeName;

    @ApiModelProperty(name = "订单账号")
    private String orderId;

    @ApiModelProperty(name = "账单收入（包含商店和配送员所得，默认商店百分之80，配送员百分之20）")
    private BigDecimal income;

    @ApiModelProperty(name = "商店收入，单个订单的百分之八十")
    private BigDecimal storeIncome;

    @ApiModelProperty(name = "平台抽取费用百分之一")
    private BigDecimal serviceFee;

}
