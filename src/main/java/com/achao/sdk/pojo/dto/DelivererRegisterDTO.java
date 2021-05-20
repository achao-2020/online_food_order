package com.achao.sdk.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "配送员创建传输对象")
public class DelivererRegisterDTO extends BaseDTO {
    private static final long serialVersionUID = 6686430371247295539L;
    @ApiModelProperty(value = "id", notes = "配送员id")
    private String id;
    @ApiModelProperty(value = "account", notes = "配送员账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "配送员密码")
    private String password;
    @ApiModelProperty(value = "name", notes = "配送员名称")
    private String name;
    @ApiModelProperty(value = "longitude", notes = "经度")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "contact", notes = "手机号")
    private String contact;
}
