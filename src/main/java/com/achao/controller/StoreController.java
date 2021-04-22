package com.achao.controller;

import com.achao.pojo.dto.BaseLoginDTO;
import com.achao.pojo.dto.BillPageDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.dto.StoreDTO;
import com.achao.pojo.vo.*;
import com.achao.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/storeService")
@Api(value = "商店接口", description = "该接口提供给商店访问")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Resource
    private StoreBillService storeBillService;

    @PostMapping("/login")
    @ApiOperation(value = "商店登陆", notes = "商店登陆接口Post", response = Result.class)
    public Result<StoreVO> login(@RequestBody BaseLoginDTO request) {
        return storeService.login(request);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建（注册）商店", notes = "当id为空的时候是创建，id有值得时候，按id更新商店信息", response = Result.class)
    public Result<StoreVO> registerStore(@RequestBody StoreDTO request) {
        return storeService.createStore(request);
    }

    @ApiOperation(value = "删除商店", notes = "用于商店的删除，通常只有管理员有这个权限")
    @PostMapping(value = "/deleteById/{id}")
    public void delete(@PathVariable String id) {
        storeService.deleteById(id);
    }

    @ApiOperation(value = "根据id查找商店", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<StoreVO> queryById(@PathVariable String id) {
        return storeService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示商店", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return storeService.queryPage(request);
    }

    @ApiOperation(value = "商店查看历史账单", notes = "商店查看历史账单")
    @PostMapping("/searchBill")
    public Result<PageVO> searchBill(@RequestBody BillPageDTO request) {
        return storeBillService.searchPage(request);
    }
}
