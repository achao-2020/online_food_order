package com.achao.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author achao
 */
@Data
@ApiModel(value = "配送员实时位置信息")
public class DeliverLocationVO {
    @ApiModelProperty(value = "longitude", name = "经度")
    private Long longitude;
    @ApiModelProperty(value = "latitude", name = "纬度")
    private Long latitude;
    @ApiModelProperty(value = "name", name = "配送员名称")
    private Long name;
    @ApiModelProperty(value = "id", name = "配送员id")
    private Long id;
}
