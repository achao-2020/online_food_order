package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("store")
public class StorePO extends BasePO {
    @TableField(value = "account", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String account;
    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;
    @TableField(value = "name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    @TableField(value = "description", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String description;
    @TableField(value = "address", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String address;
    @TableField(value = "longitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double longitude;
    @TableField(value = "latitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double latitude;
    @TableField(value = "contact", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String contact;
}
