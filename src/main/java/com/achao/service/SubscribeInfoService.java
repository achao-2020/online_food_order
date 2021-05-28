package com.achao.service;

import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.po.SubscribeInfoPO;
import com.achao.sdk.pojo.vo.SubscribeInfoVO;
import com.achao.service.mapper.SubscribeInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author aChao
 * @create: 2021-05-24 22:49
 */
@Service
public class SubscribeInfoService extends BaseService<SubscribeInfoMapper, SubscribeInfoPO, SubscribeInfoVO>{
    public SubscribeInfoVO queryById(String id) {
        SubscribeInfoPO subscribeInfoPO = this.baseMapper.selectById(id);
        SubscribeInfoVO subscribeInfoVO = new SubscribeInfoVO();
        BeanUtils.copyProperties(subscribeInfoPO, subscribeInfoVO);
        return subscribeInfoVO;
    }

    /**
     * 获取没有被推送的消息
     * @return
     */
    public synchronized List<SubscribeInfoPO> getNotSendMessageInfo() {
        QueryWrapper<SubscribeInfoPO> wrapper = new QueryWrapper<>();
        wrapper.eq("is_send", Constant.NO);
        List<SubscribeInfoPO> infos = this.baseMapper.selectList(wrapper);
        return infos;
    }

    public void update(SubscribeInfoPO infoPO, QueryWrapper<SubscribeInfoPO> wrapper) {
        this.baseMapper.update(infoPO, wrapper);
    }
}
