package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口返回对象
 */
@Data
@ApiModel(value = "接口请求返回对象", description = "接口请求返回对象")
public class Result<T> {
    @ApiModelProperty(value = "返回状态码")
    private String code;
    @ApiModelProperty(value = "是否成功，成功(true), 失败(false)")
    private boolean success;
    @ApiModelProperty(value = "返回提示信息")
    private String message;
    @ApiModelProperty(value = "返回的具体数据")
    private T info;
}
