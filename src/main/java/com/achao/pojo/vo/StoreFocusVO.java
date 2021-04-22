package com.achao.pojo.vo;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商店关注视图层对象")
public class StoreFocusVO extends BaseVO{
    @NotReflect
    private static final long serialVersionUID = -5307903908255301448L;
    @ApiModelProperty(value = "storeId", notes = "商店id")
    private String storeId;
    @ApiModelProperty(value = "customerId", notes = "顾客id")
    private String customerId;
    @ApiModelProperty(value = "storeName", notes = "商店名称")
    private String storeName;
    @ApiModelProperty(value = "customer", notes = "顾客名称")
    private String customerName;
}
