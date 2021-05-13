package com.tlk.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.entities.SysRole;
import com.tlk.blog.system.req.SysRoleREQ;
import com.tlk.blog.util.base.Result;

import java.util.List;
/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface ISysRoleService extends IService<SysRole> {

    Result queryPage(SysRoleREQ req);

    Result deleteById(String id);

    Result findMenuIdsById(String id);

    Result saveRoleMenu(String roleId, List<String> menuIds);
}
