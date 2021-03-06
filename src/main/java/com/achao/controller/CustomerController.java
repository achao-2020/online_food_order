package com.achao.controller;

import com.achao.sdk.pojo.dto.*;
import com.achao.sdk.pojo.po.StorePO;
import com.achao.sdk.pojo.vo.*;
import com.achao.service.BasketService;
import com.achao.service.CustomerService;
import com.achao.service.DelivererService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private DelivererService delivererService;

    @PostMapping("/login")
    @ApiOperation(value = "顾客登陆", notes = "顾客登陆，提供用户密码", response = Result.class)
    public Result<CustomerVO> login(@RequestBody BaseLoginDTO request) {
        return customerService.login(request);
    }

    @GetMapping("/loginOut")
    @ApiOperation(value = "顾客登出", notes = "顾客登陆，提供用户密码", response = Result.class)
    public Result<String> loginOut(@RequestParam("id")String id) {
        return customerService.loginOut(id);
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

    @ApiOperation(value = "按照条件分页显示顾客", notes = "按照条件查询分页显示, 条件为空的时候，搜索所有", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return customerService.queryPage(request);
    }

    @ApiOperation(value = "添加餐品到购物车", notes = "添加餐品到购物车", response = Result.class)
    @PostMapping("/addBasket")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<BasketVO> addBasket(@RequestBody BasketDTO request) {
        return basketService.create(request);
    }

    @ApiOperation(value = "删除购物车", notes = "输入id批量删除购物车")
    @PostMapping("/deleteBasket")
    @Transactional(rollbackFor = RuntimeException.class)
    public void  deleteBasket(@RequestBody List<String> ids) {
        basketService.deleteBath(ids);
    }

    @ApiOperation(value = "查询顾客购物栏", notes = "返回list", response = Result.class)
    @GetMapping("/queryBaskets/{id}")
    public Result<List<BasketVO>> queryBaskets(@PathVariable String id) {
        return basketService.queryBaskets(id);
    }

    @ApiOperation(value = "查找附近的商店", notes = "分页查找附近的商店", response = Result.class)
    @PostMapping("/nearbyStores")
    public Result<PageVO> nearbyStores(@RequestBody LocationDTO request) {
        return customerService.nearbyStores(request);
    }

    @ApiOperation(value = "查询配送员订单配送轨迹", notes = "通过订单查看配送员的配送轨迹", response = Result.class)
    @PostMapping("/deliverTrail/{id}")
    public Result<DeliverLocationVO> deliverTrail(@PathVariable("id") String id) {
        return delivererService.deliverTrail(id);
    }

    @ApiOperation(value = "顾客获取订阅商店推送的消息", notes = "顾客获取订阅商店推送的消息", response = Result.class)
    @GetMapping("/getSubscribeMessage/{id}")
    public Result<String> getSubscribeMessage(@PathVariable String id) {
        return customerService.getSubscribeMessage(id);
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
