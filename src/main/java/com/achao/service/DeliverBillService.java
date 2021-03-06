package com.achao.service;

import com.achao.sdk.pojo.dto.BillPageDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.SearchCriteriaPO;
import com.achao.sdk.pojo.po.DeliverBillPO;
import com.achao.sdk.pojo.po.OrderPO;
import com.achao.sdk.pojo.vo.DeliverBillVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.utils.DateUtil;
import com.achao.service.mapper.DeliverBillMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author achao
 */
@Slf4j
@Service("deliverBillService")
public class DeliverBillService extends BaseService<DeliverBillMapper, DeliverBillPO, DeliverBillVO> {

    @Resource
    private DelivererService delivererService;

    @Resource
    private OrderRefDishService orderRefDishService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    protected void addBill(OrderPO orderPO) {
        log.info("正在更新配送员的账单信息");
        log.info("正在更新商店的账单信息");
        String name = delivererService.queryById(orderPO.getDeliveredId()).getInfo().getName();
        BigDecimal priceTotal = orderRefDishService.getOrderPrice(orderPO.getId());
        DeliverBillPO deliverBillPO = new DeliverBillPO();
        deliverBillPO.setOrderId(orderPO.getId());
        deliverBillPO.setId("dbill" + DateUtil.format(new Date()));
        deliverBillPO.setDeliverId(orderPO.getDeliveredId());
        deliverBillPO.setDeliverName(name);
        deliverBillPO.setIncome(priceTotal.multiply(new BigDecimal(0.2 - 0.005)));
        deliverBillPO.setServiceFee(priceTotal.multiply(new BigDecimal(0.005)));
        super.createCurrency(deliverBillPO);
    }

    public Result<PageVO> searchBillPage(BillPageDTO billPageDTO) {
        QueryPageDTO pageDTO = super.getQueryPage(billPageDTO);
        if (!StringUtils.isBlank(billPageDTO.getId())) {
            pageDTO.getConditions().add(new SearchCriteriaPO("deliver_id", billPageDTO.getId(), "="));
        }
        return super.searchPageCurrency(pageDTO, DeliverBillPO.class, DeliverBillVO.class);
    }
}
