package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 */
@Data
@TableName("basket")
public class BasketPO extends BasePO  {
    @TableField(value = "customer_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String customerId;

    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;

    @TableField(value = "store_name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeName;

    @TableField(value = "dishes_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String dishesId;

    @TableField(value = "dishes_name", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String dishesName;

    @TableField(value = "price", updateStrategy = FieldStrategy.NOT_EMPTY)
    private BigDecimal price;

    @TableField(value = "description", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String description;

    @TableField(value = "discount", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer discount;

    @TableField(value = "photo", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String photo;

    @TableField(value = "count", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer count;
}
