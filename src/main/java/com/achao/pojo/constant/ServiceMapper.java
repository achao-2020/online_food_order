package com.achao.pojo.constant;

import com.achao.service.mapper.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceMapper {
    public static final Map<String, Class> serviceMapper= new HashMap<>();
    static {
        serviceMapper.put("AdminPO", AdminMapper.class);
        serviceMapper.put("CustomerPO", CustomerMapper.class);
        serviceMapper.put("DelivererPO", DelivererMapper.class);
        serviceMapper.put("CommentPO", CommentMapper.class);
        serviceMapper.put("DishesPO", DisheMapper.class);
        serviceMapper.put("OrderPO", OrderMapper.class);
        serviceMapper.put("StorePO", StoreMapper.class);
        serviceMapper.put("StoreFocusPO", StoreFocusMapper.class);
        serviceMapper.put("BasketPO", BasketMapper.class);
        serviceMapper.put("StoreBillPO", StoreBillMapper.class);
        serviceMapper.put("DeliverBillPO", DeliverBillMapper.class);
        serviceMapper.put("OrderRefDishesPO", OrderRefDishesMapper.class);
    }
}
