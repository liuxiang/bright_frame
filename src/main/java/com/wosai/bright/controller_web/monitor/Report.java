package com.wosai.bright.controller_web.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Controller
@RequestMapping("/API/monitor/report")
public class Report {

    @RequestMapping(method = RequestMethod.GET, value = {""})
    public void report(HttpServletRequest request, HttpServletResponse response) {
        String pram = request.getParameter("pram");

        Map<String, Object> result = new HashedMap();
        result.put("message", "GET pram:" + pram + " is received");

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

    @RequestMapping(method = RequestMethod.POST, value = {""})
    public void report_post(HttpServletRequest request, HttpServletResponse response) {
        String pram = request.getParameter("pram");

        Map<String, Object> result = new HashedMap();
        result.put("message", "POST pram:" + pram + " is received");

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
