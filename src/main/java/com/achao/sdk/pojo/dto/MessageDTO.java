package com.achao.sdk.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author aChao
 * @create: 2021-05-26 00:26
 */
@ApiModel(value = "发布消息传输类")
@Data
public class MessageDTO extends BaseDTO {
    @ApiModelProperty(value = "storeId", notes = "商店id")
    private String storeId;
    @ApiModelProperty(value = "customerIds", notes = "目标顾客，为空时为所有订阅对象")
    private List<String> customerIds;
    @ApiModelProperty(value = "title", notes = "消息标题")
    private String title;
    @ApiModelProperty(value = "message", notes = "消息内容")
    private String message;
}
