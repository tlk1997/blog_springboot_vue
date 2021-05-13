package com.tlk.blog.feign.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor // 有参构造器
@ApiModel("更新用户信息请求类")
public class UserInfoREQ implements Serializable {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickName;

    @ApiModelProperty(value = "用户头像url", required = true)
    private String userImage;

}
