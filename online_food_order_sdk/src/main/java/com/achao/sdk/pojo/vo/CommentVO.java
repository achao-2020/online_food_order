package com.achao.sdk.pojo.vo;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评论视图对象")
public class CommentVO extends BaseVO {
    @NotReflect
    private static final long serialVersionUID = 3591753482758773250L;
    @ApiModelProperty(value = "title", notes = "评论标题")
    private String title;
    @ApiModelProperty(value = "content", notes = "评论内容")
    private String content;
    @ApiModelProperty(value = "rate", notes = "评分")
    private Integer rate;
    @ApiModelProperty(value = "orderId", notes = "关联订单")
    private String orderId;
}
