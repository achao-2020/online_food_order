package com.achao.controller;

import com.achao.sdk.pojo.dto.OrderDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.vo.OrderVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.pojo.vo.StoreVO;
import com.achao.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "订单服务接口", description = "用于所有用户访问订单信息的接口")
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建订单List", notes = "用于顾客下单（订单的标志位默认为1表示准备状态）, 当id不为空时候，按照id更新订单，可以批量", response =
            Result.class)
    public Result<OrderVO> create(@RequestBody OrderDTO request) {
        return ordersService.createOrder(request);
    }

    @ApiOperation(value = "删除订单", notes = "用于订单的删除，通常只有管理员有这个权限")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        ordersService.deleteById(id);
    }

    @ApiOperation(value = "根据id查找订单", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<OrderVO> queryById(@PathVariable("id") String id) {
        return ordersService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示订单", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return ordersService.queryPage(request);
    }

    @ApiOperation(value = "完成订单", notes = "把订单的标识为改为3，为订单已完成状态")
    @GetMapping("/finish/{id}")
    public Result<OrderVO> finish(@PathVariable("id") String id) {
        return ordersService.finish(id);
    }

    @ApiOperation(value = "更改订单状态为配送中", notes = "需要传入订单id, 标志为改为2", response = Result.class)
    @GetMapping("/delivering/{id}")
    public Result<String> delivering(@PathVariable("id")String id){
        return ordersService.delivering(id);
    }
}
