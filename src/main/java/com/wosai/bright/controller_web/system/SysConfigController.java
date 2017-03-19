package com.wosai.bright.controller_web.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wosai.bright.model.SysConfig;
import com.wosai.bright.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18 0018.
 */
@Controller
@RequestMapping("/web")
public class SysConfigController {

    private static final transient Logger log = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 不建议,spring的默认注入会使用已经实现IService接口的实例类(如:CountryServiceImpl)
     * 或者发现有多个实现,将会报错:expected single matching bean but found 2: countryService,sysConfigService
     */
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    private IService iService;

    @RequestMapping(method = RequestMethod.GET, value = {"sysConfig"})
    public void report(HttpServletRequest request, HttpServletResponse response) {

        List<SysConfig> sysConfigList = sysConfigService.selectByExample(new Example(SysConfig.class));
//        List<SysConfig> sysConfigList =  iService.selectByExample(new Example(SysConfig.class));// 使用了CountryServiceImpl

        String resultOut = JSON.toJSONString(sysConfigList, SerializerFeature.DisableCircularReferenceDetect);// 原模型响应
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
