package com.wosai.bright.controller_app.user;

import com.wosai.bright.common.Constants;
import com.wosai.bright.model.AppUser;
import com.wosai.bright.service.AppTokenService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/app")
public class AppLoginController {

//    @Autowired
//    private SysOperatingService sysOperatingService;

    @Autowired
    private AppTokenService appTokenService;

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
     * 自动登录(使用密文密码登录)
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
        AppUser appUser = (AppUser) subject.getPrincipal();
        String content = "登录";
//        sysOperatingService.saveSysOperatingLog(code , appUser.getId() , content);

        final String tokenStr = appTokenService.saveAppToken(appUser.getId());
        autoLoginInfo.put("token", tokenStr);
        appUser.setPassword(null);
        autoLoginInfo.put("user", appUser);

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
