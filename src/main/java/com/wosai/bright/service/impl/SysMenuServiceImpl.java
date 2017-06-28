package com.wosai.bright.service.impl;

import com.alibaba.fastjson.JSON;
import com.wosai.bright.mapper.SysMenuMapper;
import com.wosai.bright.mapper.SysUserMenuMapper;
import com.wosai.bright.model.SysMenu;
import com.wosai.bright.model.SysUserMenu;
import com.wosai.bright.service.SysMenuService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseService<SysMenu> implements SysMenuService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    public List<SysMenu> selectByUserMenuModel(int userId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        List<SysMenu> list = sysMenuMapper.selectByUserMenuModel(condition);
        // List<Map> listMap = sysUserMenuMapper.selectByUserMenuModel(condition);// TODO Test
        return list;
    }

    public List<SysMenu> findUserMainMenuList(Short userId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        List<SysMenu> list = sysMenuMapper.findUserMainMenuList(condition);
        return list;
    }

    @Override
    public List<SysMenu> findsecondaryMenuList() {
        List<SysMenu> list = sysMenuMapper.findsecondaryMenuList();
        return list;
    }

    @Override
    public List<SysMenu> findUserMenuList(Short userId) {
        Map<String , Object> condition = new HashedMap();
        condition.put("userId",userId);
        List<SysMenu> list = sysMenuMapper.findSysMenu(condition);
        return list;
    }

    @Override
    public void userMenuSaveOrUpdate(Short userId, String menuContent) {
        List<SysUserMenu> list = JSON.parseArray(menuContent , SysUserMenu.class);
        for (SysUserMenu menu : list){
            Short menuId = menu.getMenuId();
            Map<String , Object> condition = new HashedMap();
            condition.put("userId" , userId.shortValue());
            condition.put("menuId" , menuId);
            SysUserMenu userMenu = sysUserMenuMapper.getSysUserMenu(condition);
            if (userMenu == null || userMenu.getId() == null){
                SysUserMenu newMenu = new SysUserMenu();
                newMenu.setUserId(userId.shortValue());
                newMenu.setStatus(menu.getStatus());
                newMenu.setMenuId(menuId);
                sysUserMenuMapper.insert(newMenu);
            }else {
                userMenu.setStatus(menu.getStatus());
                sysUserMenuMapper.updateByPrimaryKey(userMenu);
            }
        }
    }

}
