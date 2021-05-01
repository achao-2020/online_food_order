package com.achao.service;

import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.dto.StoreFocusDTO;
import com.achao.pojo.po.StoreFocusPO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.pojo.vo.StoreFocusVO;
import com.achao.service.mapper.StoreFocusMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.GeneralConv;
import com.achao.utils.ResponseUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Result<List<StoreFocusVO>> create(List<StoreFocusDTO> dtos) {
        Result<List<StoreFocusVO>> rs = new Result<>();
        dtos.forEach(dto -> {
            // 如果id为null或者为空，则增加一个comment
            if (StringUtils.isBlank(dto.getId())) {
                StoreFocusPO focusPO = (StoreFocusPO) super.getTo(new StoreFocusPO(), dto);
                focusPO.setId("foc" + DateUtil.format(new Date()));
                super.createCurrency(focusPO);
                StoreFocusVO focusVO = (StoreFocusVO) super.getVo(new StoreFocusVO(), focusPO);
                focusVO.setStoreName(storeService.queryById(dto.getStoreId()).getInfo().getName());
                focusVO.setCustomerName(customerService.queryById(dto.getCustomerId()).getInfo().getName());
                rs.getInfo().add(focusVO);
            }
            // 不为空的时候，按照id更新
            StoreFocusPO focusPO = (StoreFocusPO) super.getTo(new StoreFocusPO(), dto);
            int success = this.baseMapper.updateById(focusPO);
            if (success <= 0) {
                log.error("数据" + dto.getId() + "更新失败");
            }
            StoreFocusVO focusVO = (StoreFocusVO) super.getVo(new StoreFocusVO(), focusPO);
            focusVO.setStoreName(storeService.queryById(dto.getStoreId()).getInfo().getName());
            focusVO.setCustomerName(customerService.queryById(dto.getCustomerId()).getInfo().getName());
            rs.getInfo().add(focusVO);
        });
        return rs;
    }

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
}
