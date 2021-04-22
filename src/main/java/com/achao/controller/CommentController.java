package com.achao.controller;

import com.achao.pojo.dto.CommentDTO;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.vo.CommentVO;
import com.achao.pojo.vo.CustomerVO;
import com.achao.pojo.vo.PageVO;
import com.achao.pojo.vo.Result;
import com.achao.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author achao
 */
@Api(value = "评论服务接口", description = "用于用户访问评论信息的接口")
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建一个评论", notes = "用于顾客订单完成之后评价订单, 当id不为空时候，按照id更新订单", response = Result.class)
    public Result<CommentVO> create(@RequestBody CommentDTO request) {
        return commentService.create(request);
    }

    @ApiOperation(value = "删除评论", notes = "用于评论的删除，通常只有顾客有这个权限")
    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        commentService.deleteById(id);
    }

    @ApiOperation(value = "根据订单id查找评论", response = Result.class)
    @GetMapping("/queryById/{id}")
    public Result<CommentVO> queryById(@PathVariable("id") String id) {
        return commentService.queryById(id);
    }

    @ApiOperation(value = "按照条件分页显示评论", notes = "按照条件查询分页显示", response = Result.class)
    @PostMapping("/queryPage")
    public Result<PageVO> queryPage(@RequestBody QueryPageDTO request) {
        return commentService.queryPage(request);
    }
}
