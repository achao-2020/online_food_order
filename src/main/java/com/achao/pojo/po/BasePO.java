package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 所有角色的父类
 */
@Data
public class BasePO {
    /**
     * id 唯一标识
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT, updateStrategy
            = FieldStrategy.NOT_NULL)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE, insertStrategy = FieldStrategy.NEVER, updateStrategy
            = FieldStrategy.NEVER)
    private Date updateTime;
}
