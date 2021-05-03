package com.achao.sdk.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author achao
 */
@Data
@ApiModel(value = "通用视图层对象")
public class BaseVO implements Serializable {
    private static final long serialVersionUID = 7201506957079550782L;
    @ApiModelProperty(value = "id", name = "用户id")
    private String id;
    @ApiModelProperty(value = "createTime", notes = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "updateTime", notes = "更新时间")
    private Date updateTime;
}
