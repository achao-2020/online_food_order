package com.achao.sdk.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Transfer object to store Criteria attributes.
 * @author achao
 */
@Data
@ApiModel(value = "通用查询条件构造对象")
public class SearchCriteriaPO {
    @ApiModelProperty(value = "key", notes = "字段名称")
    private String key;
    @ApiModelProperty(value = "value", notes = "字段取值")
    private Object value;
    @ApiModelProperty(value = "searchPattern", notes ="字段表达式")
    private String searchPattern;

    public SearchCriteriaPO() {
    }

    public SearchCriteriaPO(String key, Object value, String searchPattern) {
        this.key = key;
        this.value = value;
        this.searchPattern = searchPattern;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Key=[" + this.key + "]\n");
        sb.append("Value=[" + this.value + "]\n");
        sb.append("Search Pattern=[" + this.searchPattern + "]\n");

        return sb.toString();
    }


}
