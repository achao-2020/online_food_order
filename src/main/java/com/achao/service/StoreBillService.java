package com.achao.service;

import com.achao.sdk.pojo.dto.BillPageDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.SearchCriteriaPO;
import com.achao.sdk.pojo.po.OrderPO;
import com.achao.sdk.pojo.po.OrderRefDishesPO;
import com.achao.sdk.pojo.po.StoreBillPO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.pojo.vo.StoreBillVO;
import com.achao.sdk.utils.DateUtil;
import com.achao.service.mapper.StoreBillMapper;
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
@Service("storeBillService")
public class StoreBillService extends BaseService<StoreBillMapper, StoreBillPO, StoreBillVO>{
    @Resource
    private DishesService dishesService;

    @Resource
    private OrderRefDishService orderRefDishService;

    @Resource
    private StoreService storeService;

    public Result<PageVO> searchPage(BillPageDTO billPageDTO) {
        QueryPageDTO queryPage = super.getQueryPage(billPageDTO);
        if (!StringUtils.isBlank(billPageDTO.getId())) {
            queryPage.getConditions().add(new SearchCriteriaPO("store_id", billPageDTO.getId(), "="));
        }
        return super.searchPageCurrency(queryPage, StoreBillPO.class, StoreBillVO.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addBill(OrderPO orderPO) {
        log.info("正在更新商店的账单信息");
        String storeId = getStoreId(orderPO);
        // 一个订单对应的总价值
        BigDecimal priceTotal = orderRefDishService.getOrderPrice(orderPO.getId());
        String name = storeService.queryById(storeId).getInfo().getName();
        StoreBillPO storeBillPO = new StoreBillPO();
        storeBillPO.setId("sbil" + DateUtil.format(new Date()));
        storeBillPO.setStoreId(storeId);
        storeBillPO.setStoreName(name);
        storeBillPO.setOrderId(orderPO.getId());
        storeBillPO.setIncome(priceTotal);
        storeBillPO.setStoreIncome(priceTotal.multiply(new BigDecimal(0.8 - 0.01)));
        storeBillPO.setServiceFee(priceTotal.multiply(new BigDecimal(0.01)));
        super.createCurrency(storeBillPO);
    }

    private String getStoreId(OrderPO orderPO) {
        OrderRefDishesPO po = new OrderRefDishesPO();
        po.setOrderId(orderPO.getId());
        return orderRefDishService.searchCurrency(po).get(0).getStoreId();
    }
}
