package com.tlk.blog.system.service;

import com.tlk.blog.entities.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.system.req.SysMenuREQ;
import com.tlk.blog.util.base.Result;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface ISysMenuService extends IService<SysMenu> {

    Result queryList(SysMenuREQ req);

    Result deleteById(String id);

    Result findUserMenuTree(String userId);
}
