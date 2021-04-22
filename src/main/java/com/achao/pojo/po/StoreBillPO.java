package com.achao.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author achao
 * 商店账单
 */
@Data
@TableName("store_bill")
public class StoreBillPO extends BasePO{
    @TableField(value = "store_id")
    private String storeId;

    @TableField(value = "store_name")
    private String storeName;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "income")
    private BigDecimal income;

    @TableField(value = "store_income")
    private BigDecimal storeIncome;

    @TableField(value = "service_fee")
    private BigDecimal serviceFee;
}
