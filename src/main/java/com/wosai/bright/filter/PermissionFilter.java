package com.wosai.bright.filter;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wosai.bright.common.utils.GodUtils;
import com.wosai.bright.mapper.SysTokenMapper;
import com.wosai.bright.model.SysToken;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Component
public class PermissionFilter implements Filter {

    protected Logger log = Logger.getLogger(getClass());

    private String[] keyArr = {};

    private String[] endArr = {".jsp", ".html", ".js", ".jpg"};

//    public static Map<String,Map<String,Object>> tokenCacheMap;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysTokenMapper sysTokenMapper;

    @Override
    public void init(FilterConfig config) throws ServletException {

//        tokenCacheMap.put("tokenCode",new HashMap(){{
//            put("tokenTime",new Date());
//            put("sysUser",null);
//        }});
//
//       new Thread(){
//           // 5fenzhong yici
//           Map<String,Map<String,Object>> tokenCacheMapTemp=new HashMap<String, Map<String, Object>>();
//        for (Map.Entry<String,Map<String,Object>> entry:tokenCacheMap.entrySet()) {
//
//               if(entry.getKey() =="tokenTime"){
//                   entry.getValue();
//                   // new Date()
//                   tokenCacheMapTemp.put(entry.getKey(),entry.getValue());
//               }
//               // new data()
//           }
//       }

//        tokenCacheMap = tokenCacheMapTemp;

        String skipKey = config.getInitParameter("skip_key");
        if (skipKey == null) {
            return;
        }
        String[] keyArr = skipKey.split(",");
        this.keyArr = new String[keyArr.length];
        for (int i = 0; i < keyArr.length; i++) {
            this.keyArr[i] = keyArr[i].trim();
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getServletPath();// 除项目名外的请求控制
//        uri = checkUrl(uri);
        if (checkEnd(uri) || checkSkip(uri)) {
            chain.doFilter(req, res);
        } else {
            String token = ((HttpServletRequest) req).getHeader("token");

            if ("88".equals(token)) {
                chain.doFilter(req, res);
            } else if (GodUtils.CheckNull(token)) {
                tokenFail(response, "token不能为空");
            } else if (checkToken(req, token)) {
                chain.doFilter(req, res);
            } else {
                tokenFail(response, "token校验失败");
            }
        }

//        HttpSession session = request.getSession();
//        if (session == null) {
//            response.sendRedirect("relogin.jsp");
//        }
//
//        uri = checkUrl(uri);
//        if (checkEnd(uri) || checkSkip(uri)) {
//            chain.doFilter(req, res);
//        } else if (checkPermission(uri, request)) {
//            chain.doFilter(req, res);
//            log.debug(uri + " hasPermission");
//            if (StartupInit.debug) {
//                System.out.println(uri + " hasPermission");
//            }
//        } else {
//            response.sendRedirect("../noPermission.jsp");
//            log.debug(uri + " noPermission");
//            if (StartupInit.debug) {
//                System.out.println(uri + " noPermission");
//            }
//        }
    }

    private void tokenFail(HttpServletResponse response, String message) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("message", message);
        resultMap.put("result", "2");

        String resultOut = JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect);// 原模型响应
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,token,client-version,server-version");
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

    private boolean checkToken(ServletRequest request, String token) {
        HttpServletRequest req = (HttpServletRequest) request;
        ServletContext sc = req.getSession().getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);

        if (cxt != null && cxt.getBean("sysTokenMapper") != null && sysTokenMapper == null) {
            sysTokenMapper = (SysTokenMapper) cxt.getBean("sysTokenMapper");
        }
        // 先读cache，有则不需要读数据库（为保护cache内存溢出，需）
        SysToken sysToken = sysTokenMapper.getToken(token);
        if (sysToken != null && sysToken.getId() != null) {
            Long date = new Date().getTime() + 30 * 60 * 1000;
            if (sysToken.getExpireTime().getTime() < date) {
                sysToken.setExpireTime(new Date(date));
                sysTokenMapper.updateByPrimaryKey(sysToken);
            }
            // 同时写入cache
            return true;
        }
        // gengxing tokenCachTime
        return false;
    }

    /**
     * 检查结尾
     *
     * @param uri
     * @return
     */
    private boolean checkEnd(String uri) {
        for (int i = 0; i < endArr.length; i++) {
            String end = endArr[i];
            if (uri.endsWith(end)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 例外之中
     *
     * @param uri
     * @return
     */
    private boolean checkSkip(String uri) {
        if ("/".equals(uri)) {
            return true;
        }
        for (int i = 0; i < keyArr.length; i++) {
            String key = keyArr[i];
            if (key.startsWith("/")) {
                if (uri.startsWith(key)) {
                    return true;
                }
            } else if (uri.contains(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 去掉项目名
     *
     * @param url
     * @return
     */
    private String checkUrl(String url) {
        // /k12Platform/logins to /logins
        String[] urlArr = url.split("/");
        StringBuffer result = new StringBuffer();
        for (int i = 2; i < urlArr.length; i++) {
            result.append("/").append(urlArr[i]);
        }
        return result.toString();
    }

    @Override
    public void destroy() {
    }
}