package com.achao.sdk.pojo.vo;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "配送员视图对象")
public class DelivererVO extends BaseVO{
    @NotReflect
    private static final long serialVersionUID = 6360877462160214408L;
    @ApiModelProperty(value = "account", notes = "配送员账号")
    private String account;
    @ApiModelProperty(value = "name", notes = "配送员名称")
    private String name;
    @ApiModelProperty(value = "rank", notes = "配送员评分")
    private Integer rank;
    @ApiModelProperty(value = "workTime", notes = "配送员工作时长(分钟)")
    private Integer workTime;
    @ApiModelProperty(value = "delivered", notes = "总的接单数量")
    private Integer delivered;
    @ApiModelProperty(value = "longitude", notes = "经度")
    private Double longitude;
    @ApiModelProperty(value = "latitude", notes = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "contact", notes = "手机号")
    private String contact;
}
