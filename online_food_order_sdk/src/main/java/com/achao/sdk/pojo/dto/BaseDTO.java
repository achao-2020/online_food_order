package com.achao.sdk.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "通用传输对象")
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 3430578734701537915L;
}
