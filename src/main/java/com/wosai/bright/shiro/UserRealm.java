package com.wosai.bright.shiro;

import com.wosai.bright.mapper.SysMenuMapper;
import com.wosai.bright.mapper.SysUserMapper;
import com.wosai.bright.model.SysMenu;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysMenuService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;

//    @Autowired
//    private SysMenuService sysMenuService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();

        final List<SysMenu> sysMenus;
        if (user.getType() > 1) {
            sysMenus = sysMenuMapper.selectAll();
        } else {
            sysMenus = sysMenuService.selectByUserMenuModel(user.getId());
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        // ...
        permsSet.add("Authority:method_1");// TODO test

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
//		info.setRoles(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", account);
        criteria.andIsNull("expireTime");
        // criteria.andEqualTo("password", password);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);

        SysUser sysUser;
        // 账号不存在
        if (sysUsers.size() > 0) {
            sysUser = sysUserMapper.selectByExample(example).get(0);
        } else {
            throw new UnknownAccountException("用户不存在");
        }

        // 密码错误
        if (!password.equals(sysUser.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        // 账号锁定
        if (sysUser.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, password, getName());// 成功登录
        return info;
    }

}
