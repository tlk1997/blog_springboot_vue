package com.tlk.blog.feign;

import com.tlk.blog.feign.req.UserInfoREQ;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "question-server", path = "/question")
public interface IFeignQuestionController {

    @ApiOperation("Feign接口-更新问题表和回答表中的用户信息")
    @PutMapping("/feign/question/user")
    boolean updateUserInfo(@RequestBody UserInfoREQ req);

}
