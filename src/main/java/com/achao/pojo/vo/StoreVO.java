package com.achao.pojo.vo;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "商店返回信息")
@Data
public class StoreVO extends BaseVO {
    @NotReflect
    private static final long serialVersionUID = 7497763975036538780L;
    @ApiModelProperty(value = "商店名称")
    private String name;
    @ApiModelProperty(value = "商店描述")
    private String description;
    @ApiModelProperty(value = "商店位置")
    private String address;
    @ApiModelProperty(value = "商店经度")
    private Double longitude;
    @ApiModelProperty(value = "商店纬度")
    private Double latitude;
    @ApiModelProperty(value = "商店电话")
    private String contact;
}
