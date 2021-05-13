package com.tlk.blog.system.controller;


import com.tlk.blog.entities.SysRole;
import com.tlk.blog.system.req.SysRoleREQ;
import com.tlk.blog.system.service.ISysRoleService;
import com.tlk.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
@Api(value = "角色管理接口", description = "角色管理接口，提供角色的增删改查")
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation("根据角色名称查询角色列表接口")
    @PostMapping("/search")
    public Result search(@RequestBody SysRoleREQ req) {
        return sysRoleService.queryPage(req);
    }


    @ApiOperation("新增角色信息接口")
    @PostMapping
    public Result save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.ok();
    }

    @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    @ApiOperation("查询角色详情接口")
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") String id) {
        return Result.ok( sysRoleService.getById(id) ) ;
    }

    @ApiOperation("更新角色信息接口")
    @PutMapping
    public Result update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateDate(new Date());
        sysRoleService.updateById(sysRole);
        return Result.ok();
    }

    @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    @ApiOperation("删除角色信息及角色菜单关系接口")
    @DeleteMapping("/{id}") // /system/role/1
    public Result delete(@PathVariable("id") String id) {
        return sysRoleService.deleteById(id);
    }


    @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    @ApiOperation("根据角色id查询拥有的菜单ids接口")
    @GetMapping("/{id}/menu/ids") // /system/role/1/menu/ids
    public Result findMenuIdsById(@PathVariable("id") String id) {
        return sysRoleService.findMenuIdsById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(allowMultiple = true, dataType = "String",
                    name = "menuIds", value = "菜单id集合", required = true )
    })
    @ApiOperation("新增角色菜单关系数据接口")
    @PostMapping("/{id}/menu/save")
    public Result saveRoleMenu(@PathVariable("id") String id,
                               @RequestBody List<String> menuIds) {
        return sysRoleService.saveRoleMenu(id, menuIds);
    }
}
