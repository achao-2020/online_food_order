package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("dishes")
public class DishesPO extends BasePO {
    @TableField(value = "name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    @TableField(value = "description", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String description;
    @TableField(value = "price", updateStrategy = FieldStrategy.NOT_EMPTY)
    private BigDecimal price;
    /**
     * 折扣
     */
    @TableField(value = "discount", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer discount;
    /**
     * 余量
     */
    @TableField(value = "remain", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer remain;
    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;
    @TableField(value = "photo", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String photo;
}
