package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用的商店查询DTO
 */
@Data
@ApiModel(value = "商店传输对象")
public class StoreDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -4539851712925772542L;
    @ApiModelProperty(value = "id", notes = "商店id")
    private String id;
    @ApiModelProperty(value = "account", notes = "账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "密码")
    private String password;
    @ApiModelProperty(value = "name", notes = "商店名称")
    private String name;
    @ApiModelProperty(value = "contact", notes = "商店联系电话")
    private String contact;
    @ApiModelProperty(value = "description", notes = "商店描述")
    private String description;
    @ApiModelProperty(value = "address", notes = "商店地址")
    private String address;
    @ApiModelProperty(value = "longitude", notes = "经度")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度", name = "122")
    private Double latitude;
}
