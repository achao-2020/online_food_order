package com.achao.sdk.pojo.dto;

import com.achao.sdk.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author achao
 */
@Data
@ApiModel(value = "分页查找传输对象")
public class QueryPageDTO extends BaseDTO{
    @NotReflect
    private static final long serialVersionUID = -2306759458674394856L;
    @ApiModelProperty(value = "size", notes = "当前页面的数量")
    private Long size;
    @ApiModelProperty(value = "current", notes = "当前页面（0为第一页）")
    private Long current;
    @ApiModelProperty(value = "conditions", notes = "查询的条件")
    private List<SearchCriteriaPO> conditions;
    @ApiModelProperty(value = "orders", notes = "排序字段，true为ASC， false为DESC" )
    private Map<String, Boolean> orders;
}
