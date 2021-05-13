package com.tlk.blog.article.feign;


import com.tlk.blog.article.service.IArticleService;
import com.tlk.blog.article.service.ILabelService;
import com.tlk.blog.entities.Label;
import com.tlk.blog.feign.IFeignArticleController;
import com.tlk.blog.feign.req.UserInfoREQ;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "被远程调用的文章微服务接口")
@RestController // 不要少了
public class FeignArticleController implements IFeignArticleController {

    @Autowired
    private ILabelService labelService;

    @Override
    public List<Label> getLabelListByIds(List<String> labelIds) {
        // IService<Label>中已经实现了此批量查询标签信息功能，mybatis-plus已经提供的
        return labelService.listByIds(labelIds);
    }

    @Autowired
    private IArticleService articleService;

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return articleService.updateUserInfo(req);
    }


}
