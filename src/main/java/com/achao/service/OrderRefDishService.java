package com.achao.service;

import com.achao.sdk.pojo.dto.DishesOrderDTO;
import com.achao.sdk.pojo.dto.OrderDTO;
import com.achao.sdk.pojo.po.OrderRefDishesPO;
import com.achao.sdk.pojo.vo.DishesVO;
import com.achao.sdk.pojo.vo.OrderRefDishesVO;
import com.achao.sdk.utils.DateUtil;
import com.achao.service.mapper.OrderRefDishesMapper;
import org.springframework.beans.BeanUtils;
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
        List<DishesOrderDTO> dishes = dto.getDishes();
        dishes.forEach(dish -> {
            OrderRefDishesPO po = new OrderRefDishesPO();
            String id = "oref" + DateUtil.format(new Date());
            DishesVO info = dishesService.queryById(dish.getDishesId()).getInfo();
            po.setId(id);
            po.setOrderId(dto.getId());
            po.setDishesId(dish.getDishesId());
            po.setStoreId(dto.getStoreId());
            po.setDishesPrice(info.getPrice());
            po.setNumber(dish.getNumber());
            super.createCurrency(po);
        });
    }

    /**
     * 通过订单id查询关联餐品
     * @param id
     * @return
     */
    public List<OrderRefDishesVO> getDishesByOrderId(String id) {
        OrderRefDishesPO po = new OrderRefDishesPO();
        po.setOrderId(id);
        List<OrderRefDishesPO> poList = super.searchCurrency(po);
        List<OrderRefDishesVO> rst = new ArrayList<>();
        poList.forEach(p -> {
            DishesVO dishesVO = dishesService.queryById(p.getDishesId()).getInfo();
            OrderRefDishesVO refDishesVO = new OrderRefDishesVO();
            BeanUtils.copyProperties(dishesVO, refDishesVO);
            refDishesVO.setNumber(p.getNumber());
            rst.add(refDishesVO);
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
        BigDecimal sum = new BigDecimal(0);
        for (OrderRefDishesPO refDishesPO : super.searchCurrency(po)) {
            // 一个餐品的价格
            BigDecimal multiply = refDishesPO.getDishesPrice().multiply(new BigDecimal(refDishesPO.getNumber()));
            sum = sum.add(multiply);
        }
        return sum;
    }
}
