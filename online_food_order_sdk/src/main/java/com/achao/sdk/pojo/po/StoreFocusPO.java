package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("store_focus")
@Data
public class StoreFocusPO extends BasePO {
    @TableField(value = "store_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String storeId;
    @TableField(value = "customer_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String customerId;
}
