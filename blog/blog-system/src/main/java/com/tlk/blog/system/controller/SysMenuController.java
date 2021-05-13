package com.tlk.blog.system.controller;


import com.tlk.blog.entities.SysMenu;
import com.tlk.blog.system.req.SysMenuREQ;
import com.tlk.blog.system.service.ISysMenuService;
import com.tlk.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
/**
 * <p>
 * 菜单信息表 前端控制器
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
@Api(value = "菜单管理接口")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation("根据菜单名称查询列表接口")
    @PostMapping("/search") // localhost:8003/system/menu/search
    public Result search(@RequestBody SysMenuREQ req) {
        return sysMenuService.queryList(req);
    }

    @ApiOperation("通过菜单ID删除菜单及子菜单接口")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return sysMenuService.deleteById(id);
    }

    @ApiOperation("新增菜单信息接口")
    @PostMapping
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("查询菜单详情接口")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true)
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") String id) {
        return Result.ok( sysMenuService.getById(id) );
    }

    @ApiOperation("更新菜单信息接口")
    @PutMapping
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateDate( new Date() );
        sysMenuService.updateById( sysMenu );
        return Result.ok();
    }

    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @ApiOperation("通过用户id查询所拥有的权限菜单树和按钮")
    @GetMapping("/user/{userId}") // get方式 /menu/user/{userId}
    public Result findUserMenuTreeAndButton(@PathVariable("userId") String userId) {
        return sysMenuService.findUserMenuTree(userId);
    }


}
