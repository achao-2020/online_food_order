package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.constant.ServiceMapper;
import com.achao.pojo.dto.BaseDTO;
import com.achao.pojo.dto.BillPageDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.BasePO;
import com.achao.pojo.po.OrderPO;
import com.achao.pojo.po.SearchCriteriaPO;
import com.achao.pojo.vo.BaseVO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.BaseType;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("baseService")
@Slf4j
public abstract class BaseService< E extends BaseMapper, T extends BasePO, D extends BaseVO> {
    @Autowired
    protected E baseMapper;

    @Autowired
    private DeliverBillService deliverBillService;

    @Autowired
    private StoreBillService storeBillService;

    public Result<PageVO>  searchPageCurrency(QueryPageDTO dto, Class<T> tClass, Class<D> dClass) {
        IPage<T> page = new Page<>();
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        // 读取dto中的条件，封装成为wrapper和page
        try {
            SQLConditionUtil.transferToWrapper(wrapper, dto);
            SQLConditionUtil.transferToIPage(page,dto);
        } catch (SQLException throwables) {
            log.error("分页查询失败" + tClass.getSimpleName());
            return ResponseUtil.simpleFail(HttpStatus.ERROR, "分页查询失败，请联系服务器开发者！");
        }
        IPage iPage = baseMapper.selectPage(page, wrapper);
        PageVO pageVO = this.getPageVO(iPage, dClass);
        return ResponseUtil.simpleSuccessInfo(pageVO);
    }


    public List<T> searchCurrency(T to) {
        try {
            QueryWrapper<T> wrapper = GeneralConv.convQueryWrapper(to);
            BaseMapper<T> mapper =
                    (BaseMapper<T>) SpringUtil.getBean(ServiceMapper.serviceMapper.get(to.getClass().getSimpleName()));
            return mapper.selectList(wrapper);

        } catch (IllegalAccessException e) {
            log.error("查询用户信息失败！");
        }
        return null;
    }

    public void updateCurrency(T to) {
        BaseMapper<T> mapper = (BaseMapper<T>) SpringUtil.getBean(ServiceMapper.serviceMapper.get(to.getClass().getSimpleName()));
        mapper.updateById(to);
    }


    public void deleteCurrency(T entity) throws SQLException {
        try {
            QueryWrapper<T> wrapper = GeneralConv.convQueryWrapper(entity);
            BaseMapper<T> mapper =
                    (BaseMapper<T>) SpringUtil.getBean(ServiceMapper.serviceMapper.get(entity.getClass().getSimpleName()));
            mapper.delete(wrapper);
        } catch (IllegalAccessException e) {
            log.error("删除有误！");
        }

    }


    public void createCurrency(T entity) {
        BaseMapper<T> mapper =
                (BaseMapper) SpringUtil.getBean(ServiceMapper.serviceMapper.get(entity.getClass().getSimpleName()));
        mapper.insert(entity);
    }

    public BasePO getTo(BasePO to, BaseDTO dto) {
        BeanUtils.copyProperties(dto, to);
        return to;
    }

    public BaseVO getVo(BaseVO vo, BasePO to) {
        BeanUtils.copyProperties(to, vo);
        return vo;
    }

    public PageVO<D> getPageVO(IPage<T> page, Class<D> dClass) {
        PageVO<D> pageVO = new PageVO();
        pageVO.setSize(page.getSize());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setTotal(page.getTotal());
        List<T> records = page.getRecords();
        List<D> list = GeneralConv.convert2List(records, dClass);
        pageVO.setInfos(list);
        return pageVO;
    }

    protected void addBill(OrderPO orderPO) {
        storeBillService.addBill(orderPO);
        deliverBillService.addBill(orderPO);
    }

    protected QueryPageDTO getQueryPage(BillPageDTO billPageDTO) {
        QueryPageDTO pageDTO = new QueryPageDTO();
        BeanUtils.copyProperties(billPageDTO, pageDTO);
        pageDTO.setConditions(new ArrayList<>());
        if (!StringUtils.isNullOrEmpty(billPageDTO.getOrderId())) {
            pageDTO.getConditions().add(new SearchCriteriaPO("order_id", billPageDTO.getOrderId(), "="));
        }
        if (billPageDTO.getStartDate() != null) {
            pageDTO.getConditions().add(new SearchCriteriaPO("create_time", billPageDTO.getStartDate(), ">="));
        }
        if (billPageDTO.getEndDate() != null) {
            pageDTO.getConditions().add(new SearchCriteriaPO("create_time", billPageDTO.getEndDate(), "<"));
        }
        return pageDTO;
    }
}
