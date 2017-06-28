package com.wosai.bright.controller_api.monitor;

import com.wosai.bright.service.ReportService;
import com.wosai.bright.websocket_tomcat.MyWebSocket;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Component
@Configurable
@EnableScheduling
@PropertySource("classpath:config.properties")
@Controller
@RequestMapping("/API/monitor")
public class ReportController {

    @Value("#{config['jobs.checkOnLine']}")
    private String checkOnLine;


    private static final transient Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET, value = {"report"})
    @ResponseBody
    public Map<String, Object> report(HttpServletRequest request, HttpServletResponse response) {
        final String pram = request.getParameter("pram");

        Map<String, Object> deviceMap = new HashedMap();
        deviceMap.put("启停控制", "0");
        deviceMap.put("启停状态", "0");
        deviceMap.put("手自动状态", "0");
        deviceMap.put("故障状态", "0");
        deviceMap.put("冷冻水供水温度设定值", "20");
        deviceMap.put("冷冻水供水温度", "31");
        deviceMap.put("冷冻水回水温度", "32");
        deviceMap.put("冷却水出水温度", "33");
        deviceMap.put("冷却水回水温度", "34");
        deviceMap.put("冷冻水供水压力", "35");
        deviceMap.put("冷冻水回水压力", "36");
        deviceMap.put("冷却水出水压力", "37");
        deviceMap.put("冷却水回水压力", "38");
        deviceMap.put("冷凝温度", "41");
        deviceMap.put("蒸发温度", "42");
        deviceMap.put("冷凝压力", "43");
        deviceMap.put("蒸发压力", "44");

        Map<String, Object> pramMap = new HashedMap();
        pramMap.put("device_name", "Chiller_1");
        pramMap.put("device_ip", "192.168.1.1");
        pramMap.put("device_info", deviceMap);

        return pramMap;
//        return new HashedMap() {{
//            put("message", "GET pram:" + pram + " is received");
//        }};
    }

    /**
     * 采集接口
     *
     * @param postReportBean
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"report"})
    @ResponseBody
    public Map<String, Object> report_post(PostReportBean postReportBean) {

        final String device_name = postReportBean.device_name;
        String device_ip = postReportBean.device_ip;
        String device_info = postReportBean.device_info;
        String ucode = postReportBean.ucode;

        try {
            reportService.report(device_name, device_ip, device_info, ucode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashedMap() {{
            put("result", "0");
            put("message", "success");
        }};
    }

    /**
     * 采集接口
     *
     * @param postReportBean
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"report_raw"})
    @ResponseBody
    public Map<String, Object> report_post_raw(@RequestBody PostReportBean postReportBean) {

        final String device_name = postReportBean.device_name;
        String device_ip = postReportBean.device_ip;
        String device_info = postReportBean.device_info;
        String ucode = postReportBean.ucode;

        try {
            reportService.report(device_name, device_ip, device_info, ucode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashedMap() {{
            put("result", "0");
            put("message", "success");
        }};
    }

    /**
     * 离线报警
     */
    @Scheduled(cron = "${jobs.checkOnLine.schedule}")
    private void offLineReport() {
        if ("true".equals(checkOnLine)) {
            CopyOnWriteArraySet<MyWebSocket> webSocketSet = MyWebSocket.getWebSocketSet();
            for (MyWebSocket item : webSocketSet) {
                try {
                    item.sendMessage("check device online");
                    reportService.saveOffLineDeviceWarning();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }


}

/**
 * 数据Bean
 */
class PostReportBean {

    public String device_name;

    public String device_ip;

    public String device_info;

    public String ucode;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }
}