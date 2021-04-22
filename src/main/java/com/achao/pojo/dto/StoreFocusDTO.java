package com.achao.pojo.dto;

import com.achao.annoation.NotReflect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商店关注传输对象")
public class StoreFocusDTO extends BaseDTO {
    @NotReflect
    private static final long serialVersionUID = 4091226744820057354L;
    @ApiModelProperty(value = "id", notes = "id")
    private String id;
    @ApiModelProperty(value = "storeId", notes = "商店id")
    private String storeId;
    @ApiModelProperty(value = "customerId", notes = "顾客id")
    private String customerId;
}
