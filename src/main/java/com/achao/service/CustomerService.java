package com.achao.service;

import com.achao.redis.RedisService;
import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.BaseLoginDTO;
import com.achao.sdk.pojo.dto.CustomerDTO;
import com.achao.sdk.pojo.dto.LocationDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.po.CustomerPO;
import com.achao.sdk.pojo.po.StorePO;
import com.achao.sdk.pojo.vo.*;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.GeneralConv;
import com.achao.sdk.utils.RedisUtils;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.CustomerMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Resource
    private RedisUtils redisUtils;

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
        CustomerPO customerPO = customerTos.get(0);
        BeanUtils.copyProperties(customerTos.get(0), customerVO);
        // 更新状态为在线
        customerPO.setStatus(1);
        this.baseMapper.updateById(customerPO);
        return ResponseUtil.simpleSuccessInfo(customerVO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
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

    /**
     * 退出登录
     * @param id
     * @return
     */
    public Result<String> loginOut(String id) {
        CustomerPO customerPO = new CustomerPO();
        customerPO.setId(id);
        customerPO.setStatus(0);
        if (this.baseMapper.updateById(customerPO) > 0) {
            return ResponseUtil.simpleSuccessInfo("注销成功！");
        }
        return ResponseUtil.simpleFail(HttpStatus.ERROR, "注销失败");
    }

    /**
     * 获取订阅商店推送的消息
     * @param id
     * @return
     */
    public Result<String> getSubscribeMessage(String id) {
        Map<Object, Object> idMessageMap = redisUtils.hmget(Constant.MESSAGE_ACCEPTER);
        String messageId = (String) idMessageMap.get(id);
        Map<Object, Object> messageMap = redisUtils.hmget(Constant.MESSAGE);
        String message = (String) messageMap.get(messageId);
        redisUtils.hmDelete(Constant.MESSAGE, messageId);
        redisUtils.hmDelete(Constant.MESSAGE_ACCEPTER, id);
        return ResponseUtil.simpleSuccessInfo(message);
    }
}
