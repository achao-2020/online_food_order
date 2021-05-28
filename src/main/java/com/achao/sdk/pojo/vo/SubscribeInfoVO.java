package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author aChao
 * @create: 2021-05-24 22:51
 */
@Data
@ApiModel(value = "定义信息视图层")
public class SubscribeInfoVO extends BaseVO{
    @ApiModelProperty(value = "storeId", notes = "商店id")
    private String storeId;
    @ApiModelProperty(value = "customerId", notes = "顾客id")
    private String customerId;
    @ApiModelProperty(value = "isSend", notes = "是否已经推送（0：未推送； 1：已推送）")
    private Integer isSend;
    @ApiModelProperty(value = "messageId", notes = "推送消息详细关联id")
    private String messageId;
}
