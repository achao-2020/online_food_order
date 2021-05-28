package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author aChao
 * @create: 2021-05-24 22:47
 */
@Data
@TableName("subscribe_message")
public class SubscribeMessagePO extends BasePO{
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
}
