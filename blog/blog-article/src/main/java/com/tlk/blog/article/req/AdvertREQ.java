package com.tlk.blog.article.req;

import com.tlk.blog.entities.Advert;
import com.tlk.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "AdvertREQ对象", description = "文章查询条件")
public class AdvertREQ extends BaseRequest<Advert> {

    @ApiModelProperty(value = "广告标题")
    private String title;

    @ApiModelProperty(value = "状态（0:禁用，1:正常）")
    private Integer status;

}
