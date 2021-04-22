package com.achao.service;

import com.achao.pojo.dto.BillPageDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.OrderPO;
import com.achao.pojo.po.SearchCriteriaPO;
import com.achao.pojo.po.StoreBillPO;
import com.achao.pojo.po.StorePO;
import com.achao.pojo.vo.*;
import com.achao.service.mapper.StoreBillMapper;
import com.achao.utils.DateUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private StoreService storeService;

    public Result<PageVO> searchPage(BillPageDTO billPageDTO) {
        QueryPageDTO queryPage = super.getQueryPage(billPageDTO);
        if (!StringUtils.isNullOrEmpty(billPageDTO.getId())) {
            queryPage.getConditions().add(new SearchCriteriaPO("store_id", billPageDTO.getId(), "="));
        }
        return super.searchPageCurrency(queryPage, StoreBillPO.class, StoreBillVO.class);
    }

    @Override
    public void addBill(OrderPO orderPO) {
        log.info("正在更新商店的账单信息");
        DishesVO dishesVO = dishesService.queryById(orderPO.getDishesId()).getInfo();
        String name = storeService.queryById(dishesVO.getStoreId()).getInfo().getName();
        StoreBillPO storeBillPO = new StoreBillPO();
        storeBillPO.setId("sbil" + DateUtil.format(new Date()));
        storeBillPO.setStoreId(dishesVO.getStoreId());
        storeBillPO.setStoreName(name);
        storeBillPO.setOrderId(orderPO.getId());
        storeBillPO.setIncome(dishesVO.getPrice());
        storeBillPO.setStoreIncome(dishesVO.getPrice().multiply(new BigDecimal(0.8 - 0.01)));
        storeBillPO.setServiceFee(dishesVO.getPrice().multiply(new BigDecimal(0.01)));
        super.createCurrency(storeBillPO);
    }
}
