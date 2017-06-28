package com.wosai.bright.mapper;

import com.wosai.bright.model.SysMenu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper extends Mapper<SysMenu>, MySqlMapper<SysMenu> {

    List selectByUserMenuModel(Map<String,Object> condition);

    List<SysMenu> findUserMainMenuList(Map<String, Object> condition);

    List<SysMenu> findSysMenu(Map<String, Object> condition);

    List<SysMenu> findsecondaryMenuList();

}