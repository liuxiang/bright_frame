package com.wosai.bright.controller_web.user;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.SysUserMapper;
import com.wosai.bright.model.SysMenu;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysMenuService;
import com.wosai.bright.service.SysUserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web")
public class UserController {

    private static final transient Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping(method = RequestMethod.GET, value = {"user"})
    @ResponseBody
    public Object findUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
//        final List<SysUser> list = sysUserService.selectByExample(new Example(SysUser.class));
        Map<String, Object> condition = new HashedMap();
        condition.put("userName", userName);
        if (type != null) {
            condition.put("type", type.shortValue());
        }
        condition.put("mobile", mobile);
        PageHelper.startPage(page, rows);
        final List<SysUser> list = sysUserService.findUser(condition);
        for (SysUser user : list) {
            List<SysMenu> menuList = sysMenuService.findUserMenuList(user.getId());
            user.setMenuList(menuList);
            user.setPassword(null);
        }
        final PageInfo pageInfo = new PageInfo(list);
        return new HashedMap() {{
            put("object", list);
            put("totalRec", pageInfo.getTotal());
            put("curRec", pageInfo.getSize());
            put("result", 0);
            put("message", "success");
        }};
    }

    @RequestMapping(method = RequestMethod.GET, value = {"user/info"})
    @ResponseBody
    public Object user(SysUser _sysUser) {
//        Example example = new Example(SysUser.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("name", _sysUser.getName());
//        criteria.andIsNull("expireTime");
//        final SysUser sysUser = sysUserMapper.selectByExample(example).get(0);
        final List<SysUser> sysUsers = sysUserMapper.select(_sysUser);
        for (SysUser sysUser : sysUsers) {
            sysUser.setPassword(null);// 保密
        }
        return new HashedMap() {{
            put("object", sysUsers);
            put("result", 0);
            put("message", "success");
        }};
    }

    /**
     * 创建新用户|更新用户信息
     *
     * @param request
     * @param response
     * @param userId
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "user")
    public Object user_saveOrUpdate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String menuContent
    ) {
        SysUser user = new SysUser();
        if (userId != null) {
            user = sysUserService.getUser(userId.shortValue());
        }
        if (content != null) {
            Map<String, Object> condition = (Map<String, Object>) JSON.parse(content);
            String password = (String) condition.get("password");
            if (password != null) {
                password = new Sha256Hash(password).toHex();
                user.setPassword(password);
            }
            String account = (String) condition.get("account");
            if (account != null) {
                user.setAccount(account);
            }
            Integer type = (Integer) condition.get("type");
//            Integer type = Integer.parseInt((String) condition.get("type")); //前段传过来为String，需转型，否则会报错
            if (type != null) {
                user.setType(type.shortValue());
            }
            String name = (String) condition.get("name");
            if (name != null) {
                user.setName(name);
            }
            String nickName = (String) condition.get("nickName");
            if (nickName != null) {
                user.setNickname(nickName);
            }
            String mobile = (String) condition.get("mobile");
            if (mobile != null) {
                user.setMobile(mobile);
            }
            String email = (String) condition.get("email");
            if (email != null) {
                user.setEmail(email);
            }
            Integer status = (Integer) condition.get("status");
            if (status != null) {
                user.setStatus(status.shortValue());
            }
            if (user.getId() == null) {
                if (user.getAccount() == null || user.getPassword() == null) {
                    return new HashedMap() {{
                        put("result", Constants.JSON_RESULT_FAIL);
                        put("message", "参数错误");
                    }};
                }
                user.setCreateTime(new Date());
                sysUserService.save(user);
            } else {
                sysUserService.updateAll(user);
            }
        }

        if (menuContent != null) {
            sysMenuService.userMenuSaveOrUpdate(user.getId(), menuContent);
        }

        return new HashedMap() {{
//            put("object", list);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 删除用户
     *
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "user/{userId}")
    public Object user_delete(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer userId) {
        SysUser user = sysUserService.getUser(userId.shortValue());
        if (user == null) {
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "参数错误");
            }};
        }
        user.setExpireTime(new Date());
        sysUserService.updateAll(user);

        return new HashedMap() {{
//            put("object", list);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 验证原密码（app修改密码）
     * @param request
     * @param response
     * @param userId 用户id
     * @param password 原密码
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "user/checkPassword")
    public Object checkPassword(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String password
    ) {

        //todo 验证数据是否合法

        if (userId != null && password != null) {
            int i = sysUserService.checkPassword(userId, new Sha256Hash(password).toHex()); //密码加密并验证
            if (i > 0) {
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_SUCCESS); //成功
                    put("message", "success");
                }};
            }
        } else {
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "数据异常"); //用户id或密码为空
            }};
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_FAIL);
            put("message", "密码错误"); //原密码错误
        }};
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "user/saveProfile")
    public Object saveProfile(HttpServletRequest request,
                              @RequestParam(required = false, value = "id") Integer id,
                              @RequestParam(required = false, value = "file") MultipartFile file) {
        // Service 受事务保护
        return sysUserService.saveProfile(request,id,file);
    }

}
