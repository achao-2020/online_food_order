package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.BaseLoginDTO;
import com.achao.pojo.dto.CustomerDTO;
import com.achao.pojo.dto.CustomerUpdateDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.AdminPO;
import com.achao.pojo.vo.*;
import com.achao.service.mapper.CustomerMapper;
import com.achao.pojo.po.CustomerPO;
import com.achao.utils.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service(value = "customerService")
@Slf4j
public class CustomerService extends BaseService<CustomerMapper, CustomerPO, CustomerVO> {

    public Result<CustomerVO> login(BaseLoginDTO dto) {
        if (StringUtils.isNullOrEmpty(dto.getAccount()) || StringUtils.isNullOrEmpty(dto.getPassword())) {
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


    public Result<CustomerVO> updateCustomer(CustomerUpdateDTO dto) {
        CustomerPO customerTo = new CustomerPO();
        BeanUtils.copyProperties(dto, customerTo);
        super.updateCurrency(customerTo);
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customerTo, customerVO);
        return ResponseUtil.simpleSuccessInfo(customerVO);
    }


    public Result<CustomerVO> createCustomer(CustomerDTO dto) {
        // 如果id为null或者为空的话，则增加一个customer
        if (StringUtils.isNullOrEmpty(dto.getId())) {
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

    private Result<List<CustomerVO>> search(CustomerDTO dto) {
        dto.setId(null);
        CustomerPO customerPO = new CustomerPO();
        BeanUtils.copyProperties(dto, customerPO);
        List<CustomerPO> customerPOS = super.searchCurrency(customerPO);
        List<CustomerVO> list = GeneralConv.convert2List(customerPOS, CustomerVO.class);
        return ResponseUtil.simpleSuccessInfo(list);
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
}
