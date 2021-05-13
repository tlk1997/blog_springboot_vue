package com.tlk.blog.article.req;

import com.tlk.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "LabelREQ对象")
public class LabelREQ extends BaseRequest {

    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;
}
