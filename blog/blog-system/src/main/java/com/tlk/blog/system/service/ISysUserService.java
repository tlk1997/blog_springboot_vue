package com.tlk.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.entities.SysUser;
import com.tlk.blog.system.req.RegisterREQ;
import com.tlk.blog.system.req.SysUserCheckPasswordREQ;
import com.tlk.blog.system.req.SysUserREQ;
import com.tlk.blog.system.req.SysUserUpdatePasswordREQ;
import com.tlk.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface ISysUserService extends IService<SysUser> {

    Result queryPage(SysUserREQ req);

    Result findRoleIdsById(String id);

    Result saveUserRole(String userId, List<String> roleIds);

    Result deleteById(String id);

    Result checkPassword(SysUserCheckPasswordREQ req);

    Result updatePassword(SysUserUpdatePasswordREQ req);

    Result update(SysUser sysUser);

    Result getUserTotal();

    Result checkUsername(String username);

    Result register(RegisterREQ req);
}
