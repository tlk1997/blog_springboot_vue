package com.tlk.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlk.blog.entities.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    boolean deleteRoleMenuByRoleId(@Param("roleId")String roleId);

    List<String> findMenuIdsById(@Param("id") String id);

    boolean saveRoleMenu(@Param("roleId") String roleId,@Param("menuIds") List<String> menuIds);
}
