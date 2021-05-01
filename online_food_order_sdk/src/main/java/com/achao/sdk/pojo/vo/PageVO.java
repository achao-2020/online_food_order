package com.achao.sdk.pojo.vo;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "页面通用传输对象", description = "用于分页查询的显示")
public class PageVO<D extends BaseVO> extends BaseVO{
    @NotReflect
    private static final long serialVersionUID = 4743060549341489041L;
    @ApiModelProperty(value = "total", notes = "总共的数据大小")
    private Long total;
    @ApiModelProperty(value = "size", notes = "当前页面的数量")
    private Long size;
    @ApiModelProperty(value = "current", notes = "当前页面（0为第一页）")
    private Long current;

    @ApiModelProperty(value = "infos", notes = "页面中的内容")
    private List<D> infos;
}
