package com.achao.sdk.pojo.vo;

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
