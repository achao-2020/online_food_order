package com.achao.service;

import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.StoreFocusDTO;
import com.achao.sdk.pojo.po.StoreFocusPO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.pojo.vo.StoreFocusVO;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.GeneralConv;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.StoreFocusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service("store_FocusService")
public class StoreFocusService extends BaseService<StoreFocusMapper, StoreFocusPO, StoreFocusVO> {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StoreService storeService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<StoreFocusVO> create(StoreFocusDTO dto) {
        StoreFocusPO focusPO = (StoreFocusPO) super.getTo(new StoreFocusPO(), dto);
        focusPO.setId("foc" + DateUtil.format(new Date()));
        super.createCurrency(focusPO);
        StoreFocusVO focusVO = (StoreFocusVO) super.getVo(new StoreFocusVO(), focusPO);
        focusVO.setStoreName(storeService.queryById(dto.getStoreId()).getInfo().getName());
        focusVO.setCustomerName(customerService.queryById(dto.getCustomerId()).getInfo().getName());
        return ResponseUtil.simpleSuccessInfo(focusVO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除订单失败，order id:" + id);
        }
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        Result<PageVO> result = super.searchPageCurrency(request, StoreFocusPO.class, StoreFocusVO.class);
        List<StoreFocusVO> infos = result.getInfo().getInfos();
        infos.forEach(storeFocusVO -> {
            storeFocusVO.setStoreName(storeService.queryById(storeFocusVO.getStoreId()).getInfo().getName());
            storeFocusVO.setCustomerName(customerService.queryById(storeFocusVO.getCustomerId()).getInfo().getName());
        });
        return result;
    }

    public Result<List<StoreFocusVO>> queryByCustomerId(String id) {
        QueryWrapper<StoreFocusPO> wrapper = new QueryWrapper<>();
        wrapper.eq("customer", id);
        List<StoreFocusPO> focusPOS = baseMapper.selectList(wrapper);
        List list = GeneralConv.convert2List(focusPOS, StoreFocusVO.class);
        return ResponseUtil.simpleSuccessInfo(list);
    }

    public Result<List<StoreFocusVO>> queryByStoreId(String id) {
        QueryWrapper<StoreFocusPO> wrapper = new QueryWrapper<>();
        wrapper.eq("store", id);
        List<StoreFocusPO> focusPOS = baseMapper.selectList(wrapper);
        List list = GeneralConv.convert2List(focusPOS, StoreFocusVO.class);
        return ResponseUtil.simpleSuccessInfo(list);
    }

    public Result<String> isExist(String customerId, String storeId) {
        QueryWrapper<StoreFocusPO> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId);
        wrapper.eq("store_id", storeId);
        List<StoreFocusPO> list = this.baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseUtil.simpleFail(HttpStatus.NOT_FOUND, "没有找到对应的关注", null);
        }
        return ResponseUtil.simpleSuccessInfo(list.get(0).getId());
    }
}
