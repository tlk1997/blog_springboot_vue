package com.tlk.blog.question.api;

import com.tlk.blog.question.service.IReplyService;
import com.tlk.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "回答管理API接口")
@RestController
@RequestMapping("/api/reply")
public class ApiReplyController {
    @Autowired
    private IReplyService replyService;

    @ApiImplicitParam(name = "questionId", value = "问题ID", required = true)
    @ApiOperation("通过问题ID递归所有的回答及子评论信息")
    @GetMapping("/list/{questionId}")
    public Result findByQuestionId(@PathVariable String questionId) {
        return replyService.findByQuestionId(questionId);
    }
}
