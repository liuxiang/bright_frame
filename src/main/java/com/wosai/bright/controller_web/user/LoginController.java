package com.wosai.bright.controller_web.user;

import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.SysTokenMapper;
import com.wosai.bright.model.SysToken;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysOperatingService;
import com.wosai.bright.service.SysTokenService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web")
public class LoginController {

    private static final transient Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysOperatingService sysOperatingService;

    @Autowired
    private SysTokenService sysTokenService;

    @Autowired
    SysTokenMapper SysTokenMapper;

    // TODO 第三方登录,待独立接口,自动登录(带登录返回的密文)

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"login"})
    @ResponseBody
    public Object Login(@RequestParam String account, @RequestParam String password) {
        return loginBase(account, new Sha256Hash(password).toHex());// sha256加密
    }

    /**
     * 自动登录(使用密文密码登录)【暂不推荐】
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"autoLogin"})
    @ResponseBody
    public Object AutoLogin(@RequestParam String account, @RequestParam String password) {
        return loginBase(account, password);
    }

    /**
     * 自动登录(使用token登录)
     *
     * @param token(必须是最近失效的token)
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"autoLoginbyToken"})
    @ResponseBody
    public Object AutoLoginbyToken(@RequestParam String token) {
        // 依据token获取用户
        SysToken sysToken = new SysToken();
        sysToken.setToken(token);
        SysToken sysToken1 = SysTokenMapper.selectOne(sysToken);

        if (sysToken1.getUserId() == null) {
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "token不存在");
            }};
        }

        Example example = new Example(SysToken.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", sysToken1.getUserId());
        criteria.andGreaterThan("id", sysToken1.getId());
        int tokenNewNum = SysTokenMapper.selectCountByExample(example);
        if (tokenNewNum > 0) {
            // 被重新登陆,自动登陆失效
            return new HashedMap() {{
//            put("object", tokenStr);
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "用户已重新登陆,自动登陆失效");
            }};
        } else {
            SysUser sysUser = SysTokenMapper.getUser(token);
            return loginBase(sysUser.getAccount(), sysUser.getPassword());
        }
    }

    public Object loginBase(String account, String password) {
        Subject subject = null;
        String message = "success";
        try {
            subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                logout();// 如果已经登录,需要先退出登录
            }
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            subject.login(token);// 触发 UserRealm.java - doGetAuthenticationInfo
        } catch (UnknownAccountException e) {
            message = e.getMessage();// 账号或密码不正确
        } catch (IncorrectCredentialsException e) {
            message = e.getMessage();// 账号或密码不正确
        } catch (LockedAccountException e) {
            message = e.getMessage();// 账号已被锁定,请联系管理员
        } catch (AuthenticationException e) {
            message = e.getMessage();// 账户验证失败
        }

        Map<String, Object> autoLoginInfo = null;
        if (subject.isAuthenticated()) {
            System.out.println("认证成功");

            // 自动登录密码
            final String finalPassword = password;
            autoLoginInfo = new HashedMap() {{
//                put("password", finalPassword);
            }};
        } else {
            return new HashedMap() {{
                put("object", "");
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "登录失败,用户不存在或密码错误");
            }};
        }

        String code = "login";
        SysUser sysUser = (SysUser) subject.getPrincipal();
        String content = "登录";
        sysOperatingService.saveSysOperatingLog(code, sysUser.getId(), content);

        final String tokenStr = sysTokenService.saveSysToken(sysUser.getId());
        autoLoginInfo.put("token", tokenStr);
        sysUser.setPassword(null);
        autoLoginInfo.put("user", sysUser);

        final String finalMessage = message;
        final Map<String, Object> finalAutoLoginInfo = autoLoginInfo;
        return new HashedMap() {{
            put("object", finalAutoLoginInfo);
//            put("object", tokenStr);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", finalMessage);
        }};
    }

    /**
     * 登出
     */
    @RequestMapping(method = RequestMethod.GET, value = {"logout"})
    @ResponseBody
    public Object logout() {

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null) {
            subject.logout();
            System.out.println("成功退出登录");
        } else {
            System.out.println("并未登录");
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }
}
