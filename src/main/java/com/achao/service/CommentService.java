package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.CommentDTO;
import com.achao.pojo.dto.DelivererDTO;
import com.achao.pojo.dto.OrderDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.OrderPO;
import com.achao.pojo.vo.*;
import com.achao.service.mapper.CommentMapper;
import com.achao.pojo.po.CommentPO;
import com.achao.pojo.po.SearchCriteriaPO;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("commentService")
public class CommentService extends BaseService<CommentMapper, CommentPO, CommentVO> {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private DelivererService delivererService;

    public Result<CommentVO> create(CommentDTO dto) {
        // 如果id为null或者为空，则增加一个comment
        if (StringUtils.isNullOrEmpty(dto.getId())) {
            CommentPO commentPO = (CommentPO) super.getTo(new CommentPO(), dto);
            commentPO.setId("com" + DateUtil.format(new Date()));
            super.createCurrency(commentPO);
            // 根据评级对配送员等级计算。
            this.handleDeliver(dto);
            CommentVO commentVO = (CommentVO) super.getVo(new CommentVO(), commentPO);
            return ResponseUtil.simpleSuccessInfo(commentVO);
        }
        // 不为空的时候，按照id更新
        CommentPO commentPO = (CommentPO) super.getTo(new CommentPO(), dto);
        int success = this.baseMapper.updateById(commentPO);
        if (success <= 0) {
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新管理员信息失败");
        }
        CommentVO commentVO = (CommentVO) super.getVo(new CommentVO(), commentPO);
        return ResponseUtil.simpleSuccessInfo(commentVO);
    }

    private void handleDeliver(CommentDTO dto) {
        String deliverId = ordersService.queryById(dto.getOrderId()).getInfo().getDeliveredId();
        Integer rate = dto.getRate();
        Integer rank = delivererService.queryById(deliverId).getInfo().getRank();
        rank = (rank + rate) / 2;
        delivererService.updateRank(deliverId, rank);
    }

    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除订单失败，order id:" + id);
        }
    }

    public Result<CommentVO> queryById(String id) {
        CommentPO commentPO = this.baseMapper.selectById(id);
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(commentPO, commentVO);
        return ResponseUtil.simpleSuccessInfo(commentVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, CommentPO.class, CommentVO.class);
    }
}
