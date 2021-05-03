package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员更新传输对象")
public class AdminUpdateDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = 774063039169043938L;
    @ApiModelProperty(value = "account", notes = "管理员账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "管理员密码")
    private String password;
    @ApiModelProperty(value = "root", notes = "是否为超级管理员，1为是，0为否")
    private String root;
    @ApiModelProperty(value = "name", notes = "管理员名称")
    private String name;
}
