package com.achao.sdk.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author aChao
 * @create: 2021-05-24 23:03
 */
@Data
@ApiModel(value = "推送消息详情视图层对象")
public class SubscribeMessageVO extends BaseVO{
    @ApiModelProperty(value = "title", notes = "消息标题")
    private String title;
    @ApiModelProperty(value = "title", notes = "消息内容")
    private String content;
}
