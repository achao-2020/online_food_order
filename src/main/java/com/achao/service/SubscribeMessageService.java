package com.achao.service;

import com.achao.sdk.pojo.po.SubscribeInfoPO;
import com.achao.sdk.pojo.po.SubscribeMessagePO;
import com.achao.sdk.pojo.vo.SubscribeMessageVO;
import com.achao.service.mapper.SubscribeMessageMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author aChao
 * @create: 2021-05-24 23:02
 */
@Service
public class SubscribeMessageService extends BaseService<SubscribeMessageMapper, SubscribeMessagePO, SubscribeMessageVO> {
    public SubscribeMessageVO queryById(String id) {
        SubscribeMessagePO messagePO = this.baseMapper.selectById(id);
        SubscribeMessageVO messageVO = new SubscribeMessageVO();
        BeanUtils.copyProperties(messagePO, messageVO);
        return messageVO;
    }
}
