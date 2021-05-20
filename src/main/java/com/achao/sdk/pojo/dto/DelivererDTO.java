package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "配送员实体类")
@Data
public class DelivererDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -7335302454262975094L;
    @ApiModelProperty(value = "id", notes = "配送员id")
    private String id;
    @ApiModelProperty(value = "account", notes = "配送员账号")
    private String account;
    @ApiModelProperty(name = "password", notes = "配送员密码")
    private String password;
    @ApiModelProperty(name = "name", notes = "配送员名称")
    private String name;
    @ApiModelProperty(name = "longitude", notes = "配送员经度")
    private Double longitude;
    @ApiModelProperty(name = "latitude", notes = "配送员纬度")
    private Double latitude;
    @ApiModelProperty(name = "contact", notes = "配送员手机号")
    private String contact;
}
