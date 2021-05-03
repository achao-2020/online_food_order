package com.achao.sdk.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment")
public class CommentPO extends BasePO {
    /**
     * 评论标题
     */
    @TableField(value = "title", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    /**
     * 评论内容
     */
    @TableField(value = "content", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    /**
     * 评分
     */
    @TableField(value = "rate", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer rate;

    /**
     * 与订单关联id
     */
    @TableField(value = "order_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String orderId;
}
