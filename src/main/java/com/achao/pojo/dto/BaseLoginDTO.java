package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登陆传输对象")
public class BaseLoginDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -5705166751562534728L;
    @ApiModelProperty(value = "account", notes = "管理员登陆账号")
    private String account;
    @ApiModelProperty(value = "password", notes = "管理员登陆密码")
    private String password;
}
