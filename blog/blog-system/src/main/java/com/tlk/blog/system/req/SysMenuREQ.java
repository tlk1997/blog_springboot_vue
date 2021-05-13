package com.tlk.blog.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SysMenuREQ对象")
public class SysMenuREQ {

    @ApiModelProperty(value = "菜单名称")
    private String name;
}
