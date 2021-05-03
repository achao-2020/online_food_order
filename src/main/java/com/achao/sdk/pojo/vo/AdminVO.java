package com.achao.sdk.pojo.vo;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员视图层对象")
public class AdminVO extends BaseVO {
    @NotReflect
    private static final long serialVersionUID = 7812370356905306128L;
    @ApiModelProperty(value = "root", name = "超级管理员标识")
    private String root;
    @ApiModelProperty(value = "name", name = "管理员名称")
    private String name;
}
