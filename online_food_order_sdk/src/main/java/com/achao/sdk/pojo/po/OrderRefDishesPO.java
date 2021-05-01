package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单餐品关联表
 * @author achao
 */
@Data
@TableName("order_ref_dishes")
public class OrderRefDishesPO extends BasePO{
    @TableField(value = "order_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String orderId;
    @TableField(value = "dishes_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String dishesId;
    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;
}
