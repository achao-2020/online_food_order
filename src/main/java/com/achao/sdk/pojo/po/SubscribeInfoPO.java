package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author aChao
 * @create: 2021-05-24 22:41
 */
@Data
@TableName("subscribe_info")
public class SubscribeInfoPO extends BasePO{
    @TableField(value = "store_id")
    private String storeId;
    @TableField(value = "customer_id")
    private String customerId;
    @TableField(value = "is_send")
    private Integer isSend;
    @TableField(value = "message_id")
    private String messageId;
}
