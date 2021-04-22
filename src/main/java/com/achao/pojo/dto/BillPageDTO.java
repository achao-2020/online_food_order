package com.achao.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@ApiModel(value = "账单传输层对象", description = "用来查询自己一定时间内的账单")
public class BillPageDTO extends BaseDTO {
    @ApiModelProperty(value = "size", notes = "当前页面的数量")
    private Long size;

    @ApiModelProperty(value = "current", notes = "当前页面（0为第一页）")
    private Long current;

    @ApiModelProperty(value = "storeId", notes = "如果是商店调用，传递商店id; 如果是配送员调用，传递配送员id")
    private String id;

    @ApiModelProperty(value = "orderId", notes = "可以传入订单id,没有传入忽略该条件")
    private String orderId;

    @ApiModelProperty(value = "账单开始时间, 没有传递则查询所有账单, >=")
    private Date startDate;

    @ApiModelProperty(value = "账单结束时间， 没有传递默认是今天, <")
    private Date endDate;

    @ApiModelProperty(value = "orders", notes = "排序字段，true为ASC， false为DESC" )
    private Map<String, Boolean> orders;
}
