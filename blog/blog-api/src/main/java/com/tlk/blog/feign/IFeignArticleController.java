package com.tlk.blog.feign;

import com.tlk.blog.entities.Label;
import com.tlk.blog.feign.req.UserInfoREQ;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// value 指定目标微服务名字，path 目标微服务的上下路径contextPath值，如果目标微服务没有配置contextPath则不需要此path。
@FeignClient(value = "article-server", path = "/article")
public interface IFeignArticleController {

    //    allowMultiple = true 表示是数组格式的参数，  dataType = "String" 数组中参数的类型
    @ApiImplicitParam(allowMultiple = true, dataType = "String"
            , name="ids", value = "标签ID集合", required = true)
    @ApiOperation("Feign接口-根据标签ids查询对应的标签信息")
    @GetMapping("/api/feign/label/list/{ids}")
    List<Label> getLabelListByIds(@PathVariable("ids") List<String> labelIds);

    @ApiOperation("Feign接口-更新文章表和评论表中的用户信息")
    @PutMapping("/feign/article/user")
    boolean updateUserInfo(@RequestBody UserInfoREQ req);

}
