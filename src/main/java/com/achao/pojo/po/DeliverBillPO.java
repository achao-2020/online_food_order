package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 * 配送员账单
 */
@Data
@TableName("deliver_bill")
public class DeliverBillPO extends BasePO {
    @TableField(value = "deliver_id")
    private String deliverId;

    @TableField(value = "deliver_name")
    private String deliverName;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "income")
    private BigDecimal income;

    @TableField(value = "service_fee")
    private BigDecimal serviceFee;
}
