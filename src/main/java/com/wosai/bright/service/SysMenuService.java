package com.wosai.bright.service;

import com.wosai.bright.model.SysMenu;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> selectByUserMenuModel(int userId);

    List<SysMenu> findUserMainMenuList(Short userId);

    List<SysMenu> findsecondaryMenuList();

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysMenu> findUserMenuList(Short userId);

    void userMenuSaveOrUpdate(Short id, String menuContent);
}
