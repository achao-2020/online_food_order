package com.achao.controller;

import com.achao.pojo.dto.*;
import com.achao.pojo.po.StorePO;
import com.achao.pojo.vo.*;
import com.achao.service.BasketService;
import com.achao.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author achao
 */
@RestController
@RequestMapping("/customerService")
@Slf4j
@Api(value = "顾客接口", description = "该接口提供给对顾客访问")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BasketService basketService;

    @PostMapping("/login")
    @ApiOperation(value = "顾客登陆", notes = "顾客登陆，提供用户密码", response = Result.class)
    public Result<CustomerVO> login(@RequestBody BaseLoginDTO request) {
        return customerService.login(request);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建（注册）顾客", notes = "创建一个顾客, 当id不为空时候，按照id更新顾客信息", response = Result.class)
    public Result<CustomerVO> createCustomer(@RequestBody CustomerDTO request) {
        return customerService.createCustomer(request);
    }

    @ApiOperation(value = "通过id删除顾客", notes = "用于顾客的删除，通常只有管理员有这个权限")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable String id) {
        customerService.deleteById(id);
    }


    @ApiOperation(value = "根据id查找顾客", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<CustomerVO> queryById(@PathVariable String id) {
        return customerService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示顾客", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return customerService.queryPage(request);
    }

    @ApiOperation(value = "添加餐品到购物车", notes = "添加餐品到购物车", response = Result.class)
    @PostMapping("/addDishes")
    public Result<BasketVO> create(@RequestBody BasketDTO request) {
        return basketService.create(request);
    }

    /**
     * 这里决定使用协同过滤算法实现对用户的智能商品推荐（还没有完成）
     */
    @PostMapping("/showByHabit")
    @ApiOperation(value = "通过顾客最经的下单浏览情况智能推荐餐品(还没有实现)", notes = "通过顾客最经的下单浏览情况智能推荐餐品(还没有实现)", response = Map.class)
    public List<StorePO> showByHabit() {
        return null;
    }


}
