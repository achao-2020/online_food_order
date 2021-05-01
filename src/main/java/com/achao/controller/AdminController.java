package com.achao.controller;

import com.achao.sdk.pojo.dto.AdminDTO;
import com.achao.sdk.pojo.dto.BaseLoginDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.vo.AdminVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author achao
 */
@Slf4j
@RestController
@Api(value = "管理员接口", description = "管理员访问接口")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation(value = "管理员登陆", notes = "管理员 登陆", response = Result.class)
    public Result<AdminVO> login(@RequestBody BaseLoginDTO request) {
        return adminService.login(request);
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建一个管理员", notes = "创建一个管理员, 当id不为空时候，更新管理员（按照id情况更新", response = Result.class)
    public Result<AdminVO> createAdmin(@RequestBody AdminDTO request) {
        return adminService.createAdmin(request);
    }

    @ApiOperation(value = "删除管理员", notes = "用于管理员的删除，通常只有管理员有这个权限")
    @PostMapping(value = "/deleteById/{id}")
    public void delete(@PathVariable String id) {
        adminService.deleteById(id);
    }

    @ApiOperation(value = "根据id查找管理员", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<AdminVO> queryById(@PathVariable String id) {
        return adminService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示管理员", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return adminService.queryPage(request);
    }
}
