package com.tlk.blog.question.api;


import com.tlk.blog.entities.Question;
import com.tlk.blog.question.service.IQuestionService;
import com.tlk.blog.util.base.BaseRequest;
import com.tlk.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "问题管理Api接口")
@RestController
@RequestMapping("/api/question")
public class ApiQuestionController {
    @Autowired
    private IQuestionService questionService;

    @ApiOperation("分页查询热门回答列表接口")
    @PostMapping("/hot")
    public Result findHotList(@RequestBody BaseRequest<Question> req){
        return questionService.findHotList(req);
    }
    @ApiOperation("分页查询最新回答列表接口")
    @PostMapping("/new")
    public Result findNewList(@RequestBody BaseRequest<Question> req){
        return questionService.findNewList(req);
    }
    @ApiOperation("分页查询等待回答列表接口")
    @PostMapping("/wait")
    public Result findWaitList(@RequestBody BaseRequest<Question> req){
        return questionService.findWaitList(req);
    }

    @ApiImplicitParam(name="labelId", value="标签ID", required=true)
    @ApiOperation("根据标签ID分页查询问答列表接口")
    @PostMapping("/list/{labelId}")
    public Result findListByLabelId(@RequestBody BaseRequest<Question> req,
                                    @PathVariable("labelId") String labelId) {
        return questionService.findListByLabelId(req, labelId);
    }

    @ApiOperation("查询问题详情接口")
    @ApiImplicitParam(name="id", value="问题ID", required=true)
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") String id) {
        return questionService.findById(id);
    }


    @ApiOperation("更新浏览数接口")
    @ApiImplicitParam(name="id", value="问题ID", required=true)
    @PutMapping("/viewCount/{id}")
    public Result updateViewCount(@PathVariable("id") String id) {
        return questionService.updateViewCount(id);
    }
}
