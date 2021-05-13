package com.tlk.blog.system.mapper;

import com.tlk.blog.entities.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findByUserId(@Param("userId") String userId);

}
