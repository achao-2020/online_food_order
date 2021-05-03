package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 经纬度，位置信息
 * @author achao
 */
@ApiModel(value = "位置封装对象")
@Data
public class LocationDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 1474877838091388461L;
    @ApiModelProperty(value = "size", notes = "当前页面的数量")
    private Long size;
    @ApiModelProperty(value = "current", notes = "当前页面（0为第一页）")
    private Long current;
    @ApiModelProperty(value = "longitude", notes = "经度(0-180)")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度(-90 - 90)")
    private Double latitude;
    @ApiModelProperty(value = "radius", notes = "搜索半径（KM）")
    private Double radius;
}
