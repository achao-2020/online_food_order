package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`order`")
public class OrderPO extends BasePO {
    /**
     * status=1时候，表示订单完成，status=2表未付款, status=3表未完成
     */
    @TableField(value = "status", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer status;
    @TableField(value = "customer_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String customerId;
    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;
    @TableField(value = "delivered_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String deliveredId;
    @TableField(value = "dishes_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String dishesId;
    @TableField(value = "finish_time", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Date finishTime;
}
