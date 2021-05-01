package com.achao.service;

import com.achao.pojo.constant.HttpStatus;
import com.achao.pojo.dto.AdminDTO;
import com.achao.pojo.dto.BaseLoginDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.AdminPO;
import com.achao.pojo.vo.AdminVO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.service.mapper.AdminMapper;
import com.achao.utils.DateUtil;
import com.achao.utils.ResponseUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author achao
 */
@Slf4j
@Service
public class AdminService extends BaseService<AdminMapper, AdminPO, AdminVO> {

    public Result<AdminVO> login(BaseLoginDTO dto) {
        if (StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPassword())) {
            log.error("账号或者密码不能为空");
            return ResponseUtil.simpleFail(HttpStatus.FORBIDDEN, "账号，密码不能为空！");
        }
        AdminPO adminPO = new AdminPO();
        BeanUtils.copyProperties(dto, adminPO);
        List<AdminPO> adminPOS = super.searchCurrency(adminPO);
        if (adminPOS == null || adminPOS.size() == 0) {
            return ResponseUtil.simpleFail(HttpStatus.NOT_FOUND, "账号或者密码不正确");
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(adminPOS.get(0), adminVO);
        return ResponseUtil.simpleSuccessInfo(adminVO);
    }

    public Result<AdminVO> createAdmin(AdminDTO dto) {
        // 如果id为null或者为空，则增加一个admin
        if (StringUtils.isBlank(dto.getId())) {
            AdminPO adminPO = (AdminPO) super.getTo(new AdminPO(), dto);
            adminPO.setId("adm" + DateUtil.format(new Date()));
            super.createCurrency(adminPO);
            AdminVO adminVO = (AdminVO) super.getVo(new AdminVO(), adminPO);
            return ResponseUtil.simpleSuccessInfo(adminVO);
        }
        // 不为空的时候，按照id更新
        AdminPO adminPO = (AdminPO) super.getTo(new AdminPO(), dto);
        int success = this.baseMapper.updateById(adminPO);
        if (success <= 0) {
            return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "更新管理员信息失败");
        }
        AdminVO adminVO = (AdminVO) super.getVo(new AdminVO(), adminPO);
        return ResponseUtil.simpleSuccessInfo(adminVO);
    }


    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除失败。 admin id:" + id);
        }
    }


    public Result<AdminVO> queryById(String id) {
        AdminPO adminPO = this.baseMapper.selectById(id);
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(adminPO, adminVO);
        return ResponseUtil.simpleSuccessInfo(adminVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request,AdminPO.class, AdminVO.class);
    }
}
