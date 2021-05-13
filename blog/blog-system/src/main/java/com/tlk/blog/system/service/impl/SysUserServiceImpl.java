package com.tlk.blog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlk.blog.entities.SysUser;
import com.tlk.blog.feign.IFeignArticleController;
import com.tlk.blog.feign.IFeignQuestionController;
import com.tlk.blog.feign.req.UserInfoREQ;
import com.tlk.blog.system.mapper.SysUserMapper;
import com.tlk.blog.system.req.RegisterREQ;
import com.tlk.blog.system.req.SysUserCheckPasswordREQ;
import com.tlk.blog.system.req.SysUserREQ;
import com.tlk.blog.system.req.SysUserUpdatePasswordREQ;
import com.tlk.blog.system.service.ISysUserService;
import com.tlk.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Override
    public Result queryPage(SysUserREQ req) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();

        if(StringUtils.isNotEmpty(req.getUsername())) {
            wrapper.like("username", req.getUsername());
        }

        if(StringUtils.isNotEmpty(req.getMobile())) {
            wrapper.like("mobile", req.getMobile());
        }

        wrapper.orderByDesc("update_date");

        return Result.ok( baseMapper.selectPage(req.getPage(), wrapper) );
    }

    @Override
    public Result findRoleIdsById(String id) {
        List<String> roleIds = baseMapper.findRoleIdsById(id);
        return Result.ok(roleIds);
    }

    @Transactional // 事务管理
    @Override
    public Result saveUserRole(String userId, List<String> roleIds) {
        // 1. 删除用户角色关系表数据
        baseMapper.deleteUserRoleByUserId(userId);
        // 2. 保存新的用户角色关系表数据
        if(CollectionUtils.isNotEmpty(roleIds)) {
            baseMapper.saveUserRole(userId, roleIds);
        }
        return Result.ok();
    }

    @Override
    public Result deleteById(String id) {
        // 1. 先查询当前用户是否存在，
        SysUser sysUser = baseMapper.selectById(id);
        if(sysUser == null) {
            return Result.error("用户不存在，删除失败");
        }
        // 2. 如果存在再进行删除
        sysUser.setIsEnabled(0); // 0 删除，1可用
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return Result.ok();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result checkPassword(SysUserCheckPasswordREQ req) {
        if(StringUtils.isEmpty(req.getUserId())) {
            return Result.error("用户ID不能为空，请重试");
        }

        if(StringUtils.isEmpty(req.getOldPassword())) {
            return Result.error("原密码不能为空，请重试");
        }

        SysUser sysUser = baseMapper.selectById(req.getUserId());
        if(sysUser == null) {
            return Result.error("用户不存在，请重试");
        }
        // matches 方法中参数1：用户输入的密码（明文），参数2: 是数据库中密码（密文）
        boolean isOk = passwordEncoder.matches( req.getOldPassword(), sysUser.getPassword() );
        if( !isOk ) {
            return Result.error("原密码输入错误");
        }

        return Result.ok();
    }

    @Override
    public Result updatePassword(SysUserUpdatePasswordREQ req) {
        if(StringUtils.isEmpty(req.getUserId())) {
            return Result.error("用户ID不能为空，请重试");
        }
        if(StringUtils.isEmpty(req.getNewPassword())) {
            return Result.error("新密码不能为空，请重试");
        }
        if(StringUtils.isEmpty(req.getRepPassword())) {
            return Result.error("确认密码不能为空，请重试");
        }
        if( !req.getNewPassword().equals( req.getRepPassword() )) {
            return Result.error("新密码与确认密码不一致，请重试");
        }

        SysUser sysUser = baseMapper.selectById(req.getUserId());
        if(sysUser == null) {
            return Result.error("用户不存在，请重试");
        }

        // 如果有原密码，则校验是否正确
        if(StringUtils.isNotEmpty(req.getOldPassword())) {
            if( !passwordEncoder.matches( req.getOldPassword(), sysUser.getPassword() ) ) {
                return Result.error("原密码输入错误");
            }
        }

        // 校验都通过，将新密码进行加密存储
        sysUser.setPassword( passwordEncoder.encode(req.getNewPassword()) );
        sysUser.setPwdUpdateDate(new Date());
        baseMapper.updateById(sysUser);

        return Result.ok();
    }

    @Autowired
    private IFeignArticleController feignArticleController;

    @Autowired
    private IFeignQuestionController feignQuestionController;

    @Transactional
    @Override
    public Result update(SysUser sysUser) {
        // 1. 查询原用户信息
        SysUser user = baseMapper.selectById(sysUser.getId());
        // 2. 判断更新的用户信息中昵称和头像是否被改变
        if( !StringUtils.equals(sysUser.getNickName(), user.getNickName())
                || !StringUtils.equals(sysUser.getImageUrl(), user.getImageUrl())
        ) {
            //   2.1 只要其中一个被改变，则调用文章和问答微服务更新用户信息
            // 更新文章微服务中的用户信息
            UserInfoREQ userInfoREQ =
                    new UserInfoREQ(sysUser.getId(), sysUser.getNickName(), sysUser.getImageUrl());
            feignArticleController.updateUserInfo(userInfoREQ);
            // 更新问答微服务用户信息
            feignQuestionController.updateUserInfo(userInfoREQ);
        }

        // 3. 更新用户信息表数据 sys_user
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        // 4. 响应更新成功
        return Result.ok();
    }

    @Override
    public Result getUserTotal() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // 帐户是否可用(1 可用，0 删除用户)
        wrapper.eq("is_enabled", 1);
        Integer total = baseMapper.selectCount(wrapper);
        return Result.ok(total);
    }

    @Override
    public Result checkUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        // 查询到则存在，存在 data=true 已被注册，不存在 data=false 未被注册
        return Result.ok( sysUser == null ? false : true );
    }

    @Override
    public Result register(RegisterREQ req) {
        if(StringUtils.isEmpty( req.getUsername() )) {
            return Result.error("用户名不能为空");
        }

        if(StringUtils.isEmpty( req.getPassword() )) {
            return Result.error("密码不能为空");
        }

        if(StringUtils.isEmpty( req.getRepPassword() )) {
            return Result.error("确认密码不能为空");
        }

        if( !StringUtils.equals(req.getPassword(), req.getRepPassword())) {
            return Result.error("两次输入的密码不一致");
        }

        // 校验用户名是否存在
        Result result = checkUsername(req.getUsername());
        // 存在 data=true 已被注册，不存在 data=false 未被注册
        if( (Boolean) result.getData() ) {
            return Result.error("用户名已经被注册，请更换一个用户名");
        }

        // 将新用户信息保存到数据库
        SysUser sysUser = new SysUser();
        sysUser.setUsername( req.getUsername() );
        sysUser.setNickName( req.getUsername() );
        sysUser.setPassword( passwordEncoder.encode( req.getPassword() ) );
        // 新增操作
        this.save(sysUser);
        return Result.ok();
    }
}
