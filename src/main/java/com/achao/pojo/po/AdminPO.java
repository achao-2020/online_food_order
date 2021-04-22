package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("admin")
public class AdminPO extends BasePO {
    @TableField(value = "account", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String account;
    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;
    @TableField(value = "root", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String root;
    @TableField(value = "name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
}
