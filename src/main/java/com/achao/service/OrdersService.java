package com.achao.service;

import com.achao.pojo.constant.Constant;
import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.OrderDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.OrderPO;
import com.achao.pojo.vo.OrderVO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.service.mapper.OrderMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service
public class OrdersService extends BaseService<OrderMapper, OrderPO, OrderVO> {

    @Autowired
    private DelivererService delivererService;

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
        OrderVO orderVO = new OrderVO();
        super.getVo(orderVO, orderPO);
        return ResponseUtil.simpleSuccessInfo(orderVO);
    }

    public Result<List<OrderVO>> createOrder(List<OrderDTO> dtos) {
        Result<List<OrderVO>> rs = new Result<>();
        rs.setInfo(new ArrayList<>());
        dtos.forEach(dto -> {

            OrderPO orderPO = (OrderPO) super.getTo(new OrderPO(), dto);
            // 如果id为null或者为空，则增加一个admin
            if (StringUtils.isNullOrEmpty(dto.getId())) {
                orderPO.setId("ord" + DateUtil.format(new Date()));
                // 订单创建的时候是提交状态的状态
                orderPO.setStatus(Constant.ORDER_CREATE);
                super.createCurrency(orderPO);
                OrderVO orderVO = (OrderVO) super.getVo(new OrderVO(), orderPO);
                rs.getInfo().add(orderVO);
            } else {
                // 不为空的时候，按照id更新
                int success = this.baseMapper.updateById(orderPO);
                if (success <= 0) {
                    log.error("数据" + dto.getId() + "更新失败");
                }
                OrderVO orderVO = (OrderVO) super.getVo(new OrderVO(), orderPO);
                rs.getInfo().add(orderVO);
            }
        });
        return rs;
    }

    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除订单失败，order id:" + id);
        }
    }

    public Result<OrderVO> queryById(String id) {
        OrderPO orderPO = this.baseMapper.selectById(id);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderPO, orderVO);
        return ResponseUtil.simpleSuccessInfo(orderVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, OrderPO.class, OrderVO.class);
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
