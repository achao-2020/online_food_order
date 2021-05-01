package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.BasketDTO;
import com.achao.pojo.po.BasketPO;
import com.achao.pojo.vo.BasketVO;
import com.achao.pojo.vo.Result;
import com.achao.service.mapper.BasketMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("basketService")
@Slf4j
public class BasketService extends BaseService<BasketMapper, BasketPO, BasketVO>{
    public Result<BasketVO> create(BasketDTO dto) {
        // 如果id为null或者为空的话，则增加一个customer
        if (StringUtils.isBlank(dto.getId())) {
            BasketPO basketPO = (BasketPO) super.getTo(new BasketPO(), dto);
            basketPO.setId("bas" + DateUtil.format(new Date()));
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
}
