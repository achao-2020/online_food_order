package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "顾客传输对象")
public class CustomerDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 603663288563899106L;
    @ApiModelProperty(value = "id", notes = "顾客id")
    private String id;
    @ApiModelProperty(value = "account", notes = "顾客账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "顾客密码")
    private String password;
    @ApiModelProperty(value = "name", notes = "顾客名称")
    private String name;
    @ApiModelProperty(value = "address", notes = "顾客地址")
    private String address;
    @ApiModelProperty(value = "longitude", notes = "经度")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "contact", notes = "手机号")
    private String contact;
}
