package com.achao.service;
import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.OrderDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.po.OrderPO;
import com.achao.sdk.pojo.vo.DishesVO;
import com.achao.sdk.pojo.vo.OrderVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author achao
 */
@Slf4j
@Service
public class OrdersService extends BaseService<OrderMapper, OrderPO, OrderVO> {

    @Resource
    private DelivererService delivererService;

    @Resource
    private DishesService dishesService;

    @Resource
    private OrderRefDishService orderRefDishService;

    @Resource
    private StoreBillService storeBillService;

    public Result<OrderVO> finish(String id) {
        OrderPO orderPO = new OrderPO();
        orderPO.setId(id);
        orderPO.setStatus(Constant.ORDER_FINISH);
        orderPO.setFinishTime(new Date());
        this.baseMapper.updateById(orderPO);
        orderPO = this.baseMapper.selectById(id);
        // 更新配送员的累积工作时间
        delivererService.updateWorkTime(orderPO);
        // 生成账单
        super.addBill(orderPO);
        return queryById(id);
    }

    public Result<OrderVO> createOrder(OrderDTO dto) {
        Result<List<OrderVO>> rst = new Result<>();

        OrderPO orderPO = (OrderPO) super.getTo(new OrderPO(), dto);
        // 如果id为null或者为空，则增加一个admin
        if (StringUtils.isBlank(dto.getId())) {
            orderPO.setId("ord" + DateUtil.format(new Date()));
            dto.setId(orderPO.getId());
            // 订单创建的时候是提交状态
            orderPO.setStatus(Constant.ORDER_CREATE);
            // 订单关联表中生成数据
            orderRefDishService.createByOrder(dto);
            super.createCurrency(orderPO);
            return queryById(orderPO.getId());
        } else {
            // 不为空的时候，按照id更新
            int success = this.baseMapper.updateById(orderPO);
            if (success <= 0) {
                log.error("数据" + dto.getId() + "更新失败");
            }
            OrderVO orderVO = (OrderVO) super.getVo(new OrderVO(), orderPO);
            List<DishesVO> dishesVOS = orderRefDishService.getDishesByOrderId(orderVO.getId());
            orderVO.setDishes(dishesVOS);
            return ResponseUtil.simpleSuccessInfo(orderVO);
        }
    }

    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除订单失败，order id:" + id);
        }
    }

    public Result<OrderVO> queryById(String id) {
        OrderPO orderPO = this.baseMapper.selectById(id);
        List<DishesVO> dishes = orderRefDishService.getDishesByOrderId(orderPO.getId());
        if (CollectionUtils.isEmpty(dishes)) {
            return null;
        }
        OrderVO orderVO = (OrderVO) super.getVo(new OrderVO(), orderPO);
        orderVO.setDishes(dishes);
        return ResponseUtil.simpleSuccessInfo(orderVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        PageVO info = super.searchPageCurrency(request, OrderPO.class, OrderVO.class).getInfo();
        setDishes(info);
        return ResponseUtil.simpleSuccessInfo(info);
    }

    private void setDishes(PageVO info) {
        List<OrderVO> orderVOS = info.getInfos();
        List<OrderVO> vos = orderVOS.stream().map(orderVO -> queryById(orderVO.getId()).getInfo()).collect(Collectors.toList());
        info.setInfos(vos);
    }

    public void setDeliverId(String orderId, String deliverId) {
        OrderPO orderPO = new OrderPO();
        orderPO.setId(orderId);
        orderPO.setDeliveredId(deliverId);
        this.baseMapper.updateById(orderPO);
    }

    public Result<String> delivering(String id) {
        OrderPO orderPO = new OrderPO();
        orderPO.setId(id);
        orderPO.setStatus(Constant.ORDER_DELIVERING);
        try {
            super.updateCurrency(orderPO);
        } catch (Exception e) {
            return ResponseUtil.simpleFail(HttpStatus.ERROR, "server error");
        }

        return ResponseUtil.simpleSuccessInfo("success");
    }
}
