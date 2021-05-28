package com.achao.service;

import com.achao.redis.RedisService;
import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.BaseLoginDTO;
import com.achao.sdk.pojo.dto.MessageDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.StoreDTO;
import com.achao.sdk.pojo.po.StorePO;
import com.achao.sdk.pojo.po.SubscribeInfoPO;
import com.achao.sdk.pojo.po.SubscribeMessagePO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.pojo.vo.StoreVO;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.StoreMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author achao
 */
@Slf4j
@Service("storeService")
public class StoreService extends BaseService<StoreMapper, StorePO, StoreVO> {

    @Autowired
    private RedisService redisService;
    @Resource
    private SubscribeInfoService subscribeInfoService;
    @Resource
    private SubscribeMessageService subscribeMessageService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<StoreVO> createStore(StoreDTO dto) {
        // 如果id为null或者为空，则增加一个store
        if (StringUtils.isBlank(dto.getId())) {
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

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<StoreVO> login(BaseLoginDTO dto) {
        if (StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPassword())) {
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

    @Transactional(rollbackFor = RuntimeException.class)
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

    public Set<Object> queryPageByName(String name) {
        String key = Constant.HOST_STORE_NAME + name;
        return redisService.queryHostName(key);
    }

    public String queryStoreName(String storeId) {
        String name = queryById(storeId).getInfo().getName();
        return name;
    }

    /**
     * 商店发布订阅消息
     * @param messageDTO
     * @return
     */
    public Result<String> publishMessage(MessageDTO messageDTO) {
        List<String> customerIds = messageDTO.getCustomerIds();
        String cusIds = org.springframework.util.StringUtils.collectionToCommaDelimitedString(customerIds);
        SubscribeMessagePO messagePO = new SubscribeMessagePO();
        messagePO.setTitle(messageDTO.getTitle());
        messagePO.setContent(messageDTO.getMessage());
        messagePO.setId(UUID.randomUUID().toString());
        subscribeMessageService.createCurrency(messagePO);
        String messageId = messagePO.getId();
        SubscribeInfoPO infoPO = new SubscribeInfoPO();
        infoPO.setStoreId(messageDTO.getStoreId());
        infoPO.setMessageId(messageId);
        infoPO.setIsSend(Constant.NO);
        infoPO.setCustomerId(cusIds);
        infoPO.setId(UUID.randomUUID().toString());
        subscribeInfoService.createCurrency(infoPO);
        return ResponseUtil.simpleSuccessInfo("推送成功！");
    }
}
