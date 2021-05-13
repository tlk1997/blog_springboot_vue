package com.tlk.blog.article.req;

import com.tlk.blog.entities.Article;
import com.tlk.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "ArticleUserREQ对象")
public class ArticleUserREQ extends BaseRequest<Article> {
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "是否公开(0:不公开，1:公开)")
    private Integer isPublic;
}
