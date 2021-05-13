package com.tlk.blog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlk.blog.entities.SysRole;
import com.tlk.blog.system.mapper.SysRoleMapper;
import com.tlk.blog.system.req.SysRoleREQ;
import com.tlk.blog.system.service.ISysRoleService;
import com.tlk.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public Result queryPage(SysRoleREQ req) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();

        // 条件查询
        if(StringUtils.isNotEmpty(req.getName())) {
            wrapper.like("name", req.getName());
        }
        // 条件分页查询
        IPage<SysRole> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Transactional // 事务管理
    @Override
    public Result deleteById(String id) {
        // 1. 删除角色信息表数据 sys_role
        baseMapper.deleteById(id);
        // 2. 删除角色菜单关系表数据 sys_role_menu
        baseMapper.deleteRoleMenuByRoleId(id);

        return Result.ok();
    }

    @Override
    public Result findMenuIdsById(String id) {
        return Result.ok( baseMapper.findMenuIdsById(id) );
    }

    @Transactional // 事务管理
    @Override
    public Result saveRoleMenu(String roleId, List<String> menuIds) {
        // 1. 先删除角色菜单关系表数据
        baseMapper.deleteRoleMenuByRoleId(roleId);
        // 2. 再保存到角色关系表数据
        if(CollectionUtils.isNotEmpty(menuIds)) {
            baseMapper.saveRoleMenu(roleId, menuIds);
        }

        return Result.ok();
    }
}
