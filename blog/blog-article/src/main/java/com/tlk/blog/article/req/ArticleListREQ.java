package com.tlk.blog.article.req;

import com.tlk.blog.entities.Article;
import com.tlk.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value="ArticleListREQ对象", description = "文章列表查询条件")
public class ArticleListREQ extends BaseRequest<Article> {

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "标签ID")
    private String labelId;
}


