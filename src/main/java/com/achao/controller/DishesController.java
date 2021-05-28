package com.achao.controller;

import com.achao.sdk.pojo.dto.DishesDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.vo.DishesVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.service.DishesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(value = "餐品对外接口", description = "提供餐品的接口服务")
@RestController
@RequestMapping("/dishes")
public class DishesController {
    @Autowired
    private DishesService dishesService;

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建餐品List", notes = "用于商店增加餐品, 当id不为空时候，按照id更新餐品信息, 可以批量", response = Result.class)
    public Result<List<DishesVO>> create(@RequestBody List<DishesDTO> request) {
        return dishesService.createOrder(request);
    }

    @ApiOperation(value = "删除餐品", notes = "用于餐品的删除，通常只有商店有这个权限")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        dishesService.deleteById(id);
    }

    @ApiOperation(value = "根据id查找餐品", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<DishesVO> queryById(@PathVariable("id") String id) {
        return dishesService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示餐品", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return dishesService.queryPage(request);
    }

    @ApiOperation(value = "根据名称搜索餐品,自动补充餐品名", notes = "拥有自动填充的功能, 返回热度搜索词从大到小排序", response = Set.class)
    @GetMapping("/queryByName")
    public Set<Object> queryPageByName(String name) {
        return dishesService.queryByName(name);
    }

    @ApiOperation(value = "餐品图片上传接口")
    @PostMapping("/uploadPhoto/{dishedId}")
    public Result<String> uploadPhoto(MultipartFile file, @PathVariable String dishedId) {
        return dishesService.uploadPhoto(file, dishedId);
    }

    @ApiOperation(value = "下载图片", response = OutputStream.class)
    @GetMapping("/downPhoto/{dishesId}")
    public ResponseEntity<byte[]> downPhoto(@PathVariable String dishesId) {
        return dishesService.downPhoto(dishesId);
    }
}
