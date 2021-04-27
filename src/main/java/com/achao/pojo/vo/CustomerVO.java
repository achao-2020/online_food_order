package com.achao.pojo.vo;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author achao
 */
@Data
@ApiModel(value = "顾客传输对象")
public class CustomerVO extends BaseVO{
    @NotReflect
    private static final long serialVersionUID = -2099961840192773335L;
    @ApiModelProperty(value = "account", notes = "账号")
    private String account;
    @ApiModelProperty(value = "name", notes = "名称")
    private String name;
    @ApiModelProperty(value = "longitude", notes = "经度")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "address", notes = "地址")
    private String address;
    @ApiModelProperty(value = "contact", notes = "手机号")
    private String contact;

}
