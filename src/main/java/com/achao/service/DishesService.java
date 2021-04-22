package com.achao.service;

import com.achao.pojo.dto.DishesDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.DishesPO;
import com.achao.pojo.vo.DishesVO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.service.mapper.DisheMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service("dishesService")
public class DishesService extends BaseService<DisheMapper, DishesPO, DishesVO> {

    public Result<List<DishesVO>> createOrder(List<DishesDTO> dtos) {
        Result<List<DishesVO>> rs = new Result<>();
        rs.setInfo(new ArrayList<>());
        dtos.forEach(dto -> {
            // 如果id为null或者为空，则增加一个dishes
            DishesPO dishesPO = (DishesPO) super.getTo(new DishesPO(), dto);
            if (StringUtils.isNullOrEmpty(dto.getId())) {
                dishesPO.setId("dis" + DateUtil.format(new Date()));
                super.createCurrency(dishesPO);
                DishesVO dishesVO = (DishesVO) super.getVo(new DishesVO(), dishesPO);
                rs.getInfo().add(dishesVO);
            } else {
                // 不为空的时候，按照id更新
                int success = this.baseMapper.updateById(dishesPO);
                if (success <= 0) {
                    log.error("数据" + dto.getId() + "更新失败");
                }
                DishesVO dishesVO = (DishesVO) super.getVo(new DishesVO(), dishesPO);
                rs.getInfo().add(dishesVO);
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

    public Result<DishesVO> queryById(String id) {
        DishesPO dishesPO = this.baseMapper.selectById(id);
        assert dishesPO != null : "dishes查询为空！";
        DishesVO dishesVO = new DishesVO();
        BeanUtils.copyProperties(dishesPO, dishesVO);
        return ResponseUtil.simpleSuccessInfo(dishesVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, DishesPO.class, DishesVO.class);
    }
}
