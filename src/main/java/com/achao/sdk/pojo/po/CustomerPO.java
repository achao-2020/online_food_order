package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer")
public class CustomerPO extends BasePO {
    @TableField(value = "account")
    private String account;
    @TableField(value = "password")
    private String password;
    @TableField(value = "name")
    private String name;
    @TableField(value = "address")
    private String address;
    @TableField(value = "longitude")
    private Double longitude;
    @TableField(value = "latitude")
    private Double latitude;
    @TableField(value = "contact")
    private String contact;
    @TableField(value = "status")
    private Integer status;
}