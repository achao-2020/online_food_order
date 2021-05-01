package com.achao.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author achao
 */
@Data
public class OrderRefDishesVO extends BaseVO{
    private String orderId;
    private String dishesId;
    private String storeId;
}
