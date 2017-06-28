package com.wosai.bright.mapper;

import com.wosai.bright.model.SysUserMenu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface SysUserMenuMapper extends Mapper<SysUserMenu>, MySqlMapper<SysUserMenu> {

    List<SysUserMenu> findSysUserMenu(Map<String , Object> condition);

    SysUserMenu getSysUserMenu(Map<String, Object> condition);
}