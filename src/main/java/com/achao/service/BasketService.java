package com.achao.service;

import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.BasketDTO;
import com.achao.sdk.pojo.po.BasketPO;
import com.achao.sdk.pojo.vo.BasketVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.GeneralConv;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.BasketMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Service("basketService")
@Slf4j
public class BasketService extends BaseService<BasketMapper, BasketPO, BasketVO>{
    @Resource
    private StoreService storeService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<BasketVO> create(BasketDTO dto) {
        // 如果id为null或者为空的话，则增加一个customer
        if (StringUtils.isBlank(dto.getId())) {
            // 如果存在购物车中，则只需要修改数量即可
            if (handleExist(dto)) {
                BasketVO basketVO = new BasketVO();
                BeanUtils.copyProperties(dto, basketVO);
                return ResponseUtil.simpleSuccessInfo(basketVO);
            }
            BasketPO basketPO = (BasketPO) super.getTo(new BasketPO(), dto);
            basketPO.setId("bas" + DateUtil.format(new Date()));
            basketPO.setStoreName(storeService.queryStoreName(dto.getStoreId()));
            super.createCurrency(basketPO);
            BasketVO basketVO = (BasketVO) super.getVo(new BasketVO(), basketPO);
            return ResponseUtil.simpleSuccessInfo(basketVO);
        }
        // 不为空的话，按id更新顾客信息
        BasketPO basketPO = (BasketPO) super.getTo(new BasketPO(), dto);
        int success = this.baseMapper.updateById(basketPO);
        if (success <= 0) {
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新购物车信息失败");
        }
        BasketVO basketVO = (BasketVO) super.getVo(new BasketVO(), basketPO);
        return ResponseUtil.simpleSuccessInfo(basketVO);
    }

    public Result<List<BasketVO>> queryBaskets(String id) {
        QueryWrapper<BasketPO> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", id);
        List<BasketPO> basketPOS = this.baseMapper.selectList(wrapper);
        return ResponseUtil.simpleSuccessInfo(GeneralConv.convert2List(basketPOS, BasketVO.class));
    }


    private boolean handleExist(BasketDTO dto) {
        String customerId = dto.getCustomerId();
        String dishesId = dto.getDishesId();
        QueryWrapper<BasketPO> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId);
        wrapper.eq("dishes_id", dishesId);
        List<BasketPO> basketPOS = this.baseMapper.selectList(wrapper);
        // 如果不存在，则直接返回
        if (CollectionUtils.isEmpty(basketPOS)) {
            return false;
        }
        Integer count = basketPOS.get(0).getCount();
        BasketPO basketPO = new BasketPO();
        basketPO.setCount(count + dto.getCount());
        dto.setCount(count + dto.getCount());
        this.baseMapper.update(basketPO, wrapper);

        return true;
    }

    /**
     * 通过id集合批量删除购物车
     * @param ids
     */
    public void deleteBath(List<String> ids) {
        QueryWrapper<BasketPO> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        this.baseMapper.delete(wrapper);
    }
}
