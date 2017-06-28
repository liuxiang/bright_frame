package com.wosai.bright.controller_web.system;

import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.SysMenuDataMapper;
import com.wosai.bright.mapper.SysMenuMapper;
import com.wosai.bright.mapper.SysUserMapper;
import com.wosai.bright.mapper.SysUserMenuMapper;
import com.wosai.bright.model.SysMenu;
import com.wosai.bright.model.SysMenuData;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysMenuService;
import com.wosai.bright.service.SysTokenService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web")
public class SysMenuController {

    private static final transient Logger log = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuDataMapper sysMenuDataMapper;

    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    @Autowired
    private SysTokenService sysTokenService;

    /**
     * 用户菜单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = {"userMenu/{userId}"})
    @ResponseBody
    public Object sysConfig(HttpServletRequest request, HttpServletResponse response, @PathVariable Short userId) {

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (sysUser == null) {
            return new HashedMap() {{
                put("object", "参数错误");
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "fail");
            }};
        }

        List<SysMenu> sysMenus;
        if (sysUser.getType() > 1) {
            sysMenus = sysMenuMapper.selectAll();
            for (SysMenu menu : sysMenus) {
                menu.setStatus(new Integer(1).shortValue());
            }
        } else {
            sysMenus = sysMenuService.selectByUserMenuModel(sysUser.getId());
        }

        // 结构化Tree
        final List<Map<String, Object>> sysMenulistMap = list2ListMap_sysMenus((short) 0, sysMenus);

        return new HashedMap() {{
            put("object", sysMenulistMap);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * list2Tree
     *
     * @param parentId
     * @param sysMenus
     * @return
     */
    private List<Map<String, Object>> list2ListMap_sysMenus(Short parentId, final List<SysMenu> sysMenus) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (final SysMenu sysMenu : sysMenus) {
            if (parentId == sysMenu.getParentId()) {
                Map<String, Object> map = new HashMap<String, Object>() {{
                    put("id", sysMenu.getId());
                    put("name", sysMenu.getName());
                    put("url", sysMenu.getUrl());
                    put("parentId", sysMenu.getParentId());
                    put("icon", sysMenu.getIcon());
                    put("perms", sysMenu.getPerms());
                    put("type", sysMenu.getType());
                    put("list", list2ListMap_sysMenus(sysMenu.getId(), sysMenus));// 返回次级子集
                    put("status", sysMenu.getStatus());
                }};
                listMap.add(map);
            }
        }
        return listMap;
    }

    /**
     * 大屏数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = {"menuData"})
    @ResponseBody
    public Object sysMenuData(HttpServletRequest request, HttpServletResponse response) {
//         final List<Map<String,Object>> sysMenuDatas_bak = sysMenuDataMapper.selectAllInfo();// 暂不需要
        String token = request.getHeader("token");
        SysUser user = sysTokenService.getUser(request);
        final List<SysMenu> sysMenus;
        if (user.getType() > 1) {
            sysMenus = sysMenuMapper.selectAll();
            for (SysMenu menu : sysMenus) {
                menu.setStatus(new Integer(1).shortValue());
            }
        } else {
            Map<String, Object> condition = new HashedMap();
            condition.put("userId", user.getId());
            sysMenus = sysMenuMapper.findSysMenu(condition);
        }

//        final List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        final List<SysMenuData> sysMenuDatas = sysMenuDataMapper.selectAll();

        return new HashedMap() {{
            put("object", listJoin2Map(sysMenus, sysMenuDatas));
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * list合并2map
     *
     * @param sysMenus
     * @param sysMenuDatas
     * @return
     */
    public List<Map<String, Object>> listJoin2Map(List<SysMenu> sysMenus, final List<SysMenuData> sysMenuDatas) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

        for (final SysMenu sysMenu : sysMenus) {
            if (sysMenu.getType() != null && sysMenu.getType() == 1) {
                continue;
            }
            if (sysMenu.getStatus() != null && sysMenu.getStatus() == 1) {
                Map<String, Object> map = new HashMap<String, Object>() {{
                    put("id", sysMenu.getId());
                    put("parentId", sysMenu.getParentId());
                    put("name", sysMenu.getName());
                    put("list", chooiceSysMenuData(sysMenu.getId(), sysMenuDatas));// 返回次级子集
                    put("status", sysMenu.getStatus());
                }};
                listMap.add(map);
            }
        }
        return listMap;
    }

    private List<SysMenuData> chooiceSysMenuData(Short menuId, List<SysMenuData> sysMenuDatas) {
        List<SysMenuData> sysMenuDataList = new ArrayList<SysMenuData>();
        for (SysMenuData sysMenuData : sysMenuDatas) {
            if (menuId == sysMenuData.getMenuId()) {
                sysMenuDataList.add(sysMenuData);
            }
        }
        return sysMenuDataList;
    }

    /**
     * 获取全部主要权限列表
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "mainMenu")
    public Object findMainMenuList(HttpServletRequest request, HttpServletResponse response) {
        final List<SysMenu> list = sysMenuService.findUserMainMenuList(null);
        return new HashedMap() {{
            put("object", list);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 获取次级权限列表
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "secondaryMenu")
    public Object findsecondaryMenuList(HttpServletRequest request, HttpServletResponse response) {
        final List<SysMenu> list = sysMenuService.findsecondaryMenuList();
        return new HashedMap() {{
            put("object", list);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 权限修改
     *
     * @param request
     * @param response
     * @param userId
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "userMenu/{userId}")
    public Object updateUserMenu(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer userId,
            @RequestParam(required = false) String content
    ) {
        sysMenuService.userMenuSaveOrUpdate(userId.shortValue(), content);
        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


}
