package com.achao.service;

import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.BaseLoginDTO;
import com.achao.sdk.pojo.dto.DelivererDTO;
import com.achao.sdk.pojo.dto.DelivererRegisterDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.po.DelivererPO;
import com.achao.sdk.pojo.po.OrderPO;
import com.achao.sdk.pojo.vo.DeliverLocationVO;
import com.achao.sdk.pojo.vo.DelivererVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.GeneralConv;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.DelivererMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service(value = "delivererService")
public class DelivererService extends BaseService<DelivererMapper, DelivererPO, DelivererVO> {
    @Autowired
    private OrdersService ordersService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<DelivererVO> createDeliver(DelivererDTO dto) {
        // 如果配送员的id为空，则创建一个配送员
        if (StringUtils.isBlank(dto.getId())) {
            DelivererPO delivererPO = (DelivererPO) super.getTo(new DelivererPO(), dto);
            delivererPO.setId("deli" + DateUtil.format(new Date()));
            super.createCurrency(delivererPO);
            DelivererVO delivererVO = (DelivererVO) super.getVo(new DelivererVO(), delivererPO);
            return ResponseUtil.simpleSuccessInfo(delivererVO);
        }
        // 如果配送员的id不为空，按id更新配送员信息
        DelivererPO delivererPO = (DelivererPO) super.getTo(new DelivererPO(), dto);
        int success = this.baseMapper.updateById(delivererPO);
        if (success <= 0) {
            log.error("更新配送员信息失败， deliver id" + dto.getId());
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新配送员信息失败");
        }
        DelivererVO delivererVO = (DelivererVO) super.getVo(new DelivererVO(), delivererPO);
        return ResponseUtil.simpleSuccessInfo(delivererVO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<DelivererVO> login(BaseLoginDTO dto) {
        if (StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPassword())) {
            log.error("账号或者密码不能为空");
            return ResponseUtil.simpleFail(HttpStatus.FORBIDDEN, "账号，密码不能为空！");
        }
        DelivererPO delivererTo = new DelivererPO();
        BeanUtils.copyProperties(dto, delivererTo);
        List<DelivererPO> delivererTos = super.searchCurrency(delivererTo);
        if (delivererTos == null || delivererTos.size() == 0) {
            return ResponseUtil.simpleFail(HttpStatus.NOT_FOUND, "账号或者密码不正确");
        }
        DelivererVO delivererVO = new DelivererVO();
        BeanUtils.copyProperties(delivererTos.get(0), delivererVO);
        return ResponseUtil.simpleSuccessInfo(delivererVO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除配送员失败， deliver id:" + id);
        }
    }

    public Result<DelivererVO> queryById(String id) {
        DelivererPO delivererPO = this.baseMapper.selectById(id);
        DelivererVO delivererVO = new DelivererVO();
        BeanUtils.copyProperties(delivererPO, delivererVO);
        return ResponseUtil.simpleSuccessInfo(delivererVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, DelivererPO.class, DelivererVO.class);
    }

    /**
     * 更新配送员的评级信息
     * @param deliverId
     * @param rank
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateRank(String deliverId, Integer rank) {
        DelivererPO delivererPO = new DelivererPO();
        delivererPO.setId(deliverId);
        delivererPO.setRank(rank);
        this.baseMapper.updateById(delivererPO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateWorkTime(OrderPO po) {
        Date createTime = po.getCreateTime();
        int workTime = DateUtil.getWorkTime(createTime);
        DelivererPO deliverer = this.baseMapper.selectById(po.getDeliveredId());
        String id = deliverer.getId();
        Integer workHour = deliverer.getWorkTime();
        DelivererPO delivererPO = new DelivererPO();
        delivererPO.setId(id);
        delivererPO.setWorkTime((workTime + workHour));
        this.baseMapper.updateById(delivererPO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void acceptOrder(String orderId, String deliverId) {
        ordersService.setDeliverId(orderId, deliverId);
    }

    public Result<DeliverLocationVO> deliverTrail(String id) {
        return null;
    }
}
