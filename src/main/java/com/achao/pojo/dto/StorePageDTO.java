package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StorePageDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -5472910040254248925L;
    @ApiModelProperty(value = "当前页面")
    private Long current;
    @ApiModelProperty(value = "页面大小")
    private Long size;
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
}
