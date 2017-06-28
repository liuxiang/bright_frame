package com.wosai.bright.controller_web.device;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.DevicePropertyLogMapper;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
@Controller
@RequestMapping("/web")
public class DevicePropertyController {

    private static final transient Logger log = LoggerFactory.getLogger(DevicePropertyController.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DevicePropertyLogMapper devicePropertyLogMapper;

    /**
     * 报表查询
     * @param request
     * @param response
     * @param content
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "devicePropertyLog")
    public Object findDevicePropertyLogList(
            HttpServletRequest request ,
            HttpServletResponse response ,
            @RequestParam(required = false) String content ,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows){
        Map<String , Object> condition = (Map<String, Object>) JSON.parse(content);
        PageHelper.startPage(page, rows);
        final List<Map<String , Object>> list = devicePropertyLogMapper.findDevicePropertyLogList(condition);

        final PageInfo pageInfo = new PageInfo(list);

        return new HashedMap() {{
            put("object", list);
            put("totalRec", pageInfo.getTotal());
            put("curRec", pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

}
