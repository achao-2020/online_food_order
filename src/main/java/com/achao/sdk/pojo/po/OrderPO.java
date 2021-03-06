package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author achao
 */
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
    @TableField(value = "delivered_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String deliveredId;
    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;
    @TableField(value = "finish_time", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Date finishTime;
    @TableField(value = "contact", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String contact;
    @TableField(value = "longitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double longitude;
    @TableField(value = "latitude", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Double latitude;
    @TableField(value = "address", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String address;
}
