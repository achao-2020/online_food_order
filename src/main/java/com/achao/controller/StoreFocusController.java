package com.achao.controller;

import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.StoreFocusDTO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.pojo.vo.StoreFocusVO;
import com.achao.service.StoreFocusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/storeFocus")
@Api(value = "关注的商店（顾客，达成合作关系的配送员）对外接口", description = "提供商店关注的服务")
public class StoreFocusController {
    @Autowired
    private StoreFocusService focusService;

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建商店关注List", notes = "可以用于顾客对商店的关注，当id为空的时候按id更新, 可以批量", response = Result.class)
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<StoreFocusVO> create(@RequestBody StoreFocusDTO request) {
        return focusService.create(request);
    }

    @ApiOperation(value = "取消关注", notes = "用于顾客取消对一个商店的关注")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        focusService.deleteById(id);
    }

    @ApiOperation(value = "根据顾客id查找一个商店关注", response = Result.class)
    @GetMapping("/queryByCustomerId{id}")
    public Result<List<StoreFocusVO>> queryByCustomerId(@PathVariable("id") String id) {
        return focusService.queryByCustomerId(id);
    }

    @ApiOperation(value = "根据商店id查找一个商店关注", response = Result.class)
    @GetMapping("/queryByStoreId{id}")
    public Result<List<StoreFocusVO>> queryByStoreId(@PathVariable("id") String id) {
        return focusService.queryByStoreId(id);
    }

    @ApiOperation(value = "按照条件分页显示关注的商店", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return focusService.queryPage(request);
    }

    @ApiOperation(value = "判断一个关注是否存在", notes = "返回布尔值", response = Result.class)
    @GetMapping("/isExist/{customerId}/{storeId}")
    public Result<String> isExist(@PathVariable("customerId") String customerId,
                                   @PathVariable("storeId") String storeId) {
        return focusService.isExist(customerId, storeId);
    }
}
