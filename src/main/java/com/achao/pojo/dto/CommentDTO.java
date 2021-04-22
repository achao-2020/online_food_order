package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评论传输对象")
public class CommentDTO extends BaseDTO {
    @NotReflect
    private static final long serialVersionUID = -2652402810182486528L;
    @ApiModelProperty(value = "id", notes = "评论id")
    private String id;

    @ApiModelProperty(value = "title", notes = "评论标题")
    private String title;

    @ApiModelProperty(value = "content", notes = "评论内容")
    private String content;

    @ApiModelProperty(value = "rate", notes = "评论评级")
    private Integer rate;

    @ApiModelProperty(value = "orderId", notes = "评论所属订单")
    private String orderId;
}
