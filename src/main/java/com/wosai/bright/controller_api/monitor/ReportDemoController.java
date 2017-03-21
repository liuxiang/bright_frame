package com.wosai.bright.controller_api.monitor;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Controller
@RequestMapping("/API/demo/monitor")
public class ReportDemoController {

    @RequestMapping(method = RequestMethod.GET, value = {"/report"})
    @ResponseBody
    public Map<String, Object> report(HttpServletRequest request, HttpServletResponse response) {
        final String pram = request.getParameter("pram");

        return new HashedMap() {{
            put("message", "GET pram:" + pram + " is received");
        }};
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/report"})
    @ResponseBody
    public Map<String, Object> report_post(HttpServletRequest request, HttpServletResponse response) {
        final String pram = request.getParameter("pram");

        return new HashedMap() {{
            put("message", "POST pram:" + pram + " is received");
        }};

//        String resultOut = JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);// 原模型响应
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json");
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (out != null) {
//            out.print(resultOut);
//            out.close();
//        }
    }

}
