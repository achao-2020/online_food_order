package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "customer注册请求对象", description = "customer注册请求对象")
public class CustomerRegisterDTO extends CustomerDTO{
    @NotReflect
    private static final long serialVersionUID = 1324479534120639378L;
    @ApiModelProperty(value = "account")
    private String account;
    @ApiModelProperty(value = "password")
    private String password;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "longitude")
    private Double longitude;
    @ApiModelProperty(value = "latitude")
    private Double latitude;
    @ApiModelProperty(value = "contact")
    private String contact;
}
