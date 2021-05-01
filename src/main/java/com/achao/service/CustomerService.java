package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.*;
import com.achao.pojo.po.StorePO;
import com.achao.pojo.vo.*;
import com.achao.redis.RedisService;
import com.achao.service.mapper.CustomerMapper;
import com.achao.pojo.po.CustomerPO;
import com.achao.utils.*;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Service(value = "customerService")
@Slf4j
public class CustomerService extends BaseService<CustomerMapper, CustomerPO, CustomerVO> {

    @Resource
    private StoreService storeService;

    @Resource
    private RedisService redisService;

    public Result<CustomerVO> login(BaseLoginDTO dto) {
        if (StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPassword())) {
            log.error("账号或者密码不能为空");
            return ResponseUtil.simpleFail(HttpStatus.FORBIDDEN, "账号，密码不能为空！");
        }
        CustomerPO customerTo = new CustomerPO();
        BeanUtils.copyProperties(dto, customerTo);
        List<CustomerPO> customerTos = super.searchCurrency(customerTo);
        if (customerTos == null || customerTos.size() == 0) {
            return ResponseUtil.simpleFail(HttpStatus.NOT_FOUND, "账号或者密码不正确");
        }
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customerTos.get(0), customerVO);
        return ResponseUtil.simpleSuccessInfo(customerVO);
    }

    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    public Result<CustomerVO> createCustomer(CustomerDTO dto) {
        // 如果id为null或者为空的话，则增加一个customer
        if (StringUtils.isBlank(dto.getId())) {
            CustomerPO customerPO = (CustomerPO) super.getTo(new CustomerPO(), dto);
            customerPO.setId("cus" + DateUtil.format(new Date()));
            super.createCurrency(customerPO);
            CustomerVO customerVO = (CustomerVO) super.getVo(new CustomerVO(), customerPO);
            return ResponseUtil.simpleSuccessInfo(customerVO);
        }
        // 不为空的话，按id更新顾客信息
        CustomerPO customerPO = (CustomerPO) super.getTo(new CustomerPO(), dto);
        int success = this.baseMapper.updateById(customerPO);
        if (success <= 0) {
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新顾客信息失败");
        }
        CustomerVO customerVO = (CustomerVO) super.getVo(new CustomerVO(), customerPO);
        return ResponseUtil.simpleSuccessInfo(customerVO);
    }

    public Result<CustomerVO> queryById(String id) {
        CustomerPO customerPO = this.baseMapper.selectById(id);
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customerPO, customerVO);
        return ResponseUtil.simpleSuccessInfo(customerVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, CustomerPO.class, CustomerVO.class);
    }

    public Result<PageVO> nearbyStores(LocationDTO locationDTO) {
        List<BaseVO> stores = GeneralConv.convert2List(storeService.searchCurrency(new StorePO()), StoreVO.class);
        // redis缓存结果集和位置信息
        redisService.addResultToGeo(stores);
        List<StoreVO> storeVOS = redisService.queryNearbyStores(new Point(locationDTO.getLongitude(),
                        locationDTO.getLatitude()),
                locationDTO.getRadius());
        PageVO<StoreVO> pageVO = new PageVO();
        Long current = locationDTO.getCurrent();
        // 请求页面大小
        Long size = locationDTO.getSize();
        // 结果集大小
        int resultSize = storeVOS.size();
        // 如果请求的页面大小大于等于返回结果集大小，则返回所有数据
        if (size >= resultSize) {
            pageVO.setCurrent(1L);
            pageVO.setSize((long) resultSize);
            pageVO.setTotal((long) resultSize);
            pageVO.setInfos(storeVOS);
            return ResponseUtil.simpleSuccessInfo(pageVO);
        }
        // 同页面大小
        int totalPage = (int) Math.ceil(resultSize / size.doubleValue());
        // 计算当前页面的开始位置
        int start = (int) ((current - 1) * size);
        int end = 0;
        List<StoreVO> vos = null;
        // 当前页面为最后一页
        if (current == totalPage) {
            vos = storeVOS.subList(start, resultSize);
        } else {
            // 计算当前页面的最后一页位置
            end = Math.toIntExact(size * current);
            vos = storeVOS.subList(start, end);
        }
        // 设置参数
        pageVO.setCurrent(current);
        pageVO.setSize((long) vos.size());
        pageVO.setTotal((long) resultSize);
        pageVO.setInfos(vos);
        return ResponseUtil.simpleSuccessInfo(pageVO);
    }
}
