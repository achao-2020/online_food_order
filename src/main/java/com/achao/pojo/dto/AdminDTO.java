package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员传输对象")
public class AdminDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 3465333664551993842L;
    @ApiModelProperty(value = "id", notes = "管理员id")
    private String id;
    @ApiModelProperty(value = "account", notes = "管理员账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "管理员密码")
    private String password;
    @ApiModelProperty(value = "root", notes = "是否为超级管理员，1为是，0为否")
    private String root;
    @ApiModelProperty(value = "name", notes = "管理员名称")
    private String name;
}
