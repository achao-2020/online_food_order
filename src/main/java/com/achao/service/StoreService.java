package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.*;
import com.achao.pojo.po.StorePO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.pojo.vo.StoreVO;
import com.achao.service.mapper.StoreMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service("storeService")
public class StoreService extends BaseService<StoreMapper, StorePO, StoreVO> {

    public Result<StoreVO> createStore(StoreDTO dto) {
        // 如果id为null或者为空，则增加一个store
        if (StringUtils.isNullOrEmpty(dto.getId())) {
            StorePO storePO = (StorePO) super.getTo(new StorePO(), dto);
            storePO.setId("sto" + DateUtil.format(new Date()));
            super.createCurrency(storePO);
            StoreVO storeVO = (StoreVO) super.getVo(new StoreVO(), storePO);
            return ResponseUtil.simpleSuccessInfo(storeVO);
        }
        // 不为空的时候，按照id更新
        StorePO storePO = (StorePO) super.getTo(new StorePO(), dto);
        int success = this.baseMapper.updateById(storePO);
        if (success <= 0) {
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新管理员信息失败");
        }
        StoreVO storeVO = (StoreVO) super.getVo(new StoreVO(), storePO);
        return ResponseUtil.simpleSuccessInfo(storeVO);
    }

    
    public Result<StoreVO> updateStore(StoreDTO dto) {
        StorePO storeTo = new StorePO();
        BeanUtils.copyProperties(dto, storeTo);
        super.updateCurrency(storeTo);
        StoreVO storeVO = new StoreVO();
        BeanUtils.copyProperties(storeTo, storeVO);
        return ResponseUtil.simpleSuccessInfo(storeVO);
    }

    /**
     * 按条件查询商店，并分页
     * @param storeDTOPage
     * @return
     */
    
    public IPage<StorePO> searchPageStores(StorePageDTO storeDTOPage) {
        return null;
    }
    
    public Result<StoreVO> login(BaseLoginDTO dto) {
        if (StringUtils.isNullOrEmpty(dto.getAccount()) || StringUtils.isNullOrEmpty(dto.getPassword())) {
            log.error("账号或者密码不能为空");
            return ResponseUtil.simpleFail(HttpStatus.FORBIDDEN, "账号，密码不能为空！");
        }
        StorePO storePO = new StorePO();
        BeanUtils.copyProperties(dto, storePO);
        List<StorePO> storeTos = super.searchCurrency(storePO);
        if (storeTos == null || storeTos.size() == 0) {
            return ResponseUtil.simpleFail(HttpStatus.NOT_FOUND, "账号或者密码不正确");
        }
        StoreVO storeVO = new StoreVO();
        BeanUtils.copyProperties(storeTos.get(0), storeVO);
        return ResponseUtil.simpleSuccessInfo(storeVO);
    }

    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除失败。 admin id:" + id);
        }
    }

    public Result<StoreVO> queryById(String id) {
        StorePO storePO = this.baseMapper.selectById(id);
        StoreVO storeVO = new StoreVO();
        BeanUtils.copyProperties(storePO, storeVO);
        return ResponseUtil.simpleSuccessInfo(storeVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, StorePO.class, StoreVO.class);
    }
}
