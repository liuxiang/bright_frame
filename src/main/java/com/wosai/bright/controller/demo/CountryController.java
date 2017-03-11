package com.wosai.bright.controller.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.model.Country;
import com.wosai.bright.service.CountryService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzh_3nofxnp
 * @since 2015-09-19 17:15
 */
@Controller
@RequestMapping("/demo")
public class CountryController {

    private static final transient Logger log = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

//    private String page_list = "index";
//    private String redirect_list = "redirect:list";

    private String page_list = "index.jsp";
    private String page_view = "view.jsp";
    private String redirect_list = "redirect:list";

    @RequestMapping(value = {"list", "index", "index.html", ""})
    public ModelAndView getList(Country country,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int rows) {
        ModelAndView result = new ModelAndView(page_list);
        List<Country> countryList = countryService.selectByCountry(country, page, rows);
//        List<Country> countryList = countryService.selectByCountry_sql(country, page, rows);
        result.addObject("pageInfo", new PageInfo<Country>(countryList));
        result.addObject("queryParam", country);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }

    @RequestMapping(value = {"list_sql"})
    public ModelAndView getList_sql(Country country,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int rows) {
        ModelAndView result = new ModelAndView(page_list);
//        List<Country> countryList = countryService.selectByCountry(country, page, rows);
        List<Country> countryList = countryService.selectByCountry_sql(country, page, rows);
        result.addObject("pageInfo", new PageInfo<Country>(countryList));
        result.addObject("queryParam", country);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView view(Country country) {
        ModelAndView result = new ModelAndView(page_view);
        if (country.getId() != null) {
            country = countryService.selectByKey(country.getId());
        }
        result.addObject("country", country);
        return result;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ModelAndView save(Country country) {
        ModelAndView result = new ModelAndView(redirect_list);
        if (country.getId() != null) {
            countryService.updateAll(country);
        } else {
            countryService.save(country);
        }
        return result;
    }

    @RequestMapping("delete")
    public String delete(Integer id) {
        countryService.delete(id);
        return redirect_list;
    }

    /******************************************************************/

    @RequestMapping("login")
    public Object login(@RequestParam(required = false) String username
            , @RequestParam(required = false) String password, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("username:" + username + " password:" + password);
        Map<String, Object> r = new HashMap<String, Object>();
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            //sha256加密
            password = new Sha256Hash(password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);// 触发 UserRealm.java - doGetAuthenticationInfo
        } catch (UnknownAccountException e) {
            r.put("code", 1);
        } catch (IncorrectCredentialsException e) {
            r.put("code", 2);
        } catch (LockedAccountException e) {
            r.put("code", 3);
        } catch (AuthenticationException e) {
            r.put("code", 4);
        }
        r.put("code", 0);

        if (subject.isAuthenticated()) {
            System.out.println("认证成功");

            //=====================使用编码方式进行权限和角色的验证==================
            //是否有role1这个角色
            if (subject.hasRole("role1")) {
                System.out.println("有角色role1");
            } else {
                System.out.println("没有角色role1");
            }
            //是否有对打印机进行打印操作的权限
            if (subject.isPermitted("printer:print")) {
                System.out.println("可以对打印机进行打印操作");
            } else {
                System.out.println("不可以对打印机进行打印操作");
            }
        }

//        return r;//  TODO 定向页面 http://localhost:8080/demo/login?username=&password= (异常)
        return "redirect:/home.html";
    }

    /**
     * 退出
     */
    @RequestMapping(value = "shior/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null) {
            subject.logout();
            System.out.println("成功退出登录");
        } else {
            System.out.println("并未登录");
        }
        return "redirect:/login.html";
    }

    @RequestMapping("/testAnnotation/method_1")
    @RequiresPermissions("Authority:method_1")
    public void testAnnotation2(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("method_1 有权限");

        Map<String, Object> result = new HashedMap();
        result.put("message", "method_1 有权限");

        String resultOut = JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);// 原模型响应
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (out != null) {
            out.print(resultOut);
            out.close();
        }
    }

    @RequestMapping("/testAnnotation/method_2")
    @RequiresPermissions("Authority:method_2")
    public void testAnnotation(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("method_2 有权限");

        Map<String, Object> result = new HashedMap();
        result.put("message", "method_2 有权限");

        String resultOut = JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);// 原模型响应
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (out != null) {
            out.print(resultOut);
            out.close();
        }
    }
}
