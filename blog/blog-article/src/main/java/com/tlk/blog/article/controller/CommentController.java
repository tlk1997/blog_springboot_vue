package com.tlk.blog.article.controller;


import com.tlk.blog.article.service.ICommentService;
import com.tlk.blog.entities.Comment;
import com.tlk.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论信息表 前端控制器
 * </p>
 *
 * @author tlk
 * @since 2021-05-10
 */
@Api(value = "评论管理接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;


    @ApiImplicitParam(name = "id", value = "评论ID", required = true)
    @ApiOperation("删除评论接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return commentService.deleteById(id);
    }

    @ApiOperation("新增评论信息接口")
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return Result.ok();
    }
}
