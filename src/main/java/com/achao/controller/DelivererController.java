package com.achao.controller;

import com.achao.sdk.pojo.dto.*;
import com.achao.sdk.pojo.vo.DelivererVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.service.DeliverBillService;
import com.achao.service.DelivererService;
import com.achao.service.StoreBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author achao
 */
@Slf4j
@RestController
@RequestMapping("/delivererService")
@Api(value = "配送员接口", description = "该接口提供给配送员访问")
public class DelivererController {
    @Autowired
    private DelivererService delivererService;

    @Resource
    private DeliverBillService deliverBillService;

    @PostMapping("/login")
    @ApiOperation(value = "配送员登陆", notes = "配送员登陆Post", response = Map.class)
    public Result<DelivererVO> login(@RequestBody BaseLoginDTO retuest) {
        return delivererService.login(retuest);
    }

    @PostMapping("/create")
    @ApiOperation(value = "配送员创建（注册）", notes = "创建一个管理员, 当id不为空时候，按照id更新配送员信息", response = Map.class)
    public Result<DelivererVO>  createDeliverer(@RequestBody DelivererDTO retuest) {
        return delivererService.createDeliver(retuest);
    }

    @ApiOperation(value = "通过id删除配送员", notes = "用于配送员的删除，通常只有管理员有这个权限")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable String id) {
        delivererService.deleteById(id);
    }

    @ApiOperation(value = "根据id查找管理员")
    @GetMapping("/queryById/{id}")
    public Result<DelivererVO> queryById(@PathVariable String id) {
        return delivererService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示配送员", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return delivererService.queryPage(request);
    }

    @ApiOperation(value = "配送员接受订单", notes = "配送员确认订单，准备派送")
    @GetMapping("/acceptOrder")
    public void acceptOrder(@RequestParam("orderId") String orderId, @RequestParam("deliverId") String deliverId) {
        delivererService.acceptOrder(orderId, deliverId);
    }

    @ApiOperation(value = "配送员查看历史账单", notes = "配送员查看劳动所得")
    @PostMapping("/searchBill")
    public Result<PageVO> searchBill(@RequestBody BillPageDTO request) {
        return deliverBillService.searchBillPage(request);
    }
}
