package com.achao.service;

import com.achao.sdk.pojo.dto.OrderDTO;
import com.achao.sdk.pojo.po.OrderRefDishesPO;
import com.achao.sdk.pojo.vo.DishesVO;
import com.achao.sdk.pojo.vo.OrderRefDishesVO;
import com.achao.sdk.utils.DateUtil;
import com.achao.service.mapper.OrderRefDishesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author achao
 */
@Service
public class OrderRefDishService extends BaseService<OrderRefDishesMapper, OrderRefDishesPO, OrderRefDishesVO>{

    @Resource
    private DishesService dishesService;

    public void createByOrder(OrderDTO dto) {
        List<String> dishIds = dto.getDishIds();
        dishIds.forEach(dishId -> {
            OrderRefDishesPO po = new OrderRefDishesPO();
            String id = "oref" + DateUtil.format(new Date());
            po.setId(id);
            po.setOrderId(dto.getId());
            po.setDishesId(dishId);
            po.setStoreId(dto.getStoreId());
            super.createCurrency(po);
        });
    }

    /**
     * 通过订单id查询关联餐品
     * @param id
     * @return
     */
    public List<DishesVO> getDishesByOrderId(String id) {
        OrderRefDishesPO po = new OrderRefDishesPO();
        po.setOrderId(id);
        List<OrderRefDishesPO> poList = super.searchCurrency(po);
        List<DishesVO> rst = new ArrayList<>();
        poList.forEach(p -> {
            DishesVO dishesVO = dishesService.queryById(p.getDishesId()).getInfo();
            rst.add(dishesVO);
        });
        return rst;
    }

    /**
     * 通过订单id计算商品的总价格
     * @param id
     * @return
     */
    public BigDecimal getOrderPrice(String id) {
        OrderRefDishesPO po = new OrderRefDishesPO();
        po.setOrderId(id);
        List<String> dishIds = super.searchCurrency(po).stream().map(result -> result.getDishesId()).collect(Collectors.toList());
        BigDecimal sum = new BigDecimal(0);
        for (String dishId : dishIds) {
            BigDecimal price = dishesService.queryById(dishId).getInfo().getPrice();
            sum = sum.add(price);
        }
        return sum;
    }
}
