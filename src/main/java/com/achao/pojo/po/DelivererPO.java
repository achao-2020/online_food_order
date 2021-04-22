package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("deliverer")
public class DelivererPO extends BasePO {
    @TableField(value = "account", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String account;
    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;
    @TableField(value = "name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    @TableField(value = "rank", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer rank;
    @TableField(value = "work_time", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer workTime;
    @TableField(value = "delivered", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer delivered;
    @TableField(value = "longitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double longitude;
    @TableField(value = "latitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double latitude;
    @TableField(value = "contract", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String contract;
}
