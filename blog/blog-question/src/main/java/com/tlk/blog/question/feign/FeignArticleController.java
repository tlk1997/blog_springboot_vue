package com.tlk.blog.question.feign;

import com.tlk.blog.feign.IFeignQuestionController;
import com.tlk.blog.feign.req.UserInfoREQ;
import com.tlk.blog.question.service.IQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "被远程调用的问题微服务接口")
@RestController
public class FeignArticleController implements IFeignQuestionController {

    @Autowired
    private IQuestionService questionService;

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return questionService.updateUserInfo(req);
    }
}
