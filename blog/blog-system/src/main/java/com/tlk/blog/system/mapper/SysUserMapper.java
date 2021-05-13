package com.tlk.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlk.blog.entities.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> findRoleIdsById(@Param("id") String id);

    boolean deleteUserRoleByUserId(@Param("userId") String userId);

    boolean saveUserRole(@Param("userId") String userId,
                         @Param("roleIds") List<String> roleIds);

}
