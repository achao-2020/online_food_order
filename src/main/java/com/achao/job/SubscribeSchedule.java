package com.achao.job;

import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.dto.Message;
import com.achao.sdk.pojo.po.SubscribeInfoPO;
import com.achao.sdk.pojo.vo.StoreFocusVO;
import com.achao.sdk.pojo.vo.SubscribeMessageVO;
import com.achao.sdk.utils.RedisUtils;
import com.achao.service.StoreFocusService;
import com.achao.service.StoreService;
import com.achao.service.SubscribeInfoService;
import com.achao.service.SubscribeMessageService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author aChao
 * @create: 2021-05-26 21:26
 */
@Configuration
@EnableScheduling
@Slf4j
public class SubscribeSchedule {
    @Resource
    private SubscribeInfoService subscribeInfoService;
    @Resource
    private StoreFocusService storeFocusService;
    @Resource
    private SubscribeMessageService subscribeMessageService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private StoreService storeService;

    /**
     * 消息订阅发布定时器
     */
    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
//        log.info("正在发送订阅消息");
        // 获取没有发送的订阅消息
        List<SubscribeInfoPO> infos = subscribeInfoService.getNotSendMessageInfo();
        infos.stream().forEach(info -> {
            String storeId = info.getStoreId();
            // 获取关注的顾客id
            List<StoreFocusVO> storeFocusVOList = storeFocusService.queryByStoreId(storeId).getInfo();
            List<String> acceptCustomers = null;
            if (CollectionUtils.isNotEmpty(storeFocusVOList)) {
                acceptCustomers = storeFocusVOList.stream().map(storeFocusVO -> storeFocusVO.getCustomerId()).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(acceptCustomers)) {
                    return;
                }
            }
            // 存储订阅消息到redis
            String messageId = info.getMessageId();
            SubscribeMessageVO messageVO = subscribeMessageService.queryById(messageId);
            Message message = new Message();
            message.setStoreId(storeId);
            String storeName = storeService.queryById(storeId).getInfo().getName();
            message.setStoreName(storeName);
            message.setTitle(messageVO.getTitle());
            message.setContent(messageVO.getContent());
            Map<String, Object> messageVOMap = new HashMap<>();
            String hashKey = UUID.randomUUID().toString();
            String messageJson = JSONObject.toJSONString(messageVO);
            messageVOMap.put(hashKey, messageJson);
            redisUtils.hmset(Constant.MESSAGE, messageVOMap);
            Map<String, Object> customerIdMap = acceptCustomers.stream().collect(Collectors.toMap(id -> id, id -> hashKey));
            redisUtils.hmset(Constant.MESSAGE_ACCEPTER, customerIdMap);
            // 标注对应的订阅消息为已发送状态
            markMessageInfoIsSend(infos);
        });
    }

    private void markMessageInfoIsSend(List<SubscribeInfoPO> infos) {
        List<String> storeIds = infos.stream().map(info -> info.getStoreId()).collect(Collectors.toList());
        SubscribeInfoPO infoPO = new SubscribeInfoPO();
        infoPO.setIsSend(Constant.YES);
        QueryWrapper<SubscribeInfoPO> wrapper = new QueryWrapper<>();
        wrapper.in("store_id", storeIds);
        subscribeInfoService.update(infoPO, wrapper);

    }
}
