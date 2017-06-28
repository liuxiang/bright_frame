package com.wosai.bright.controller_web.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.model.SysOperatingLog;
import com.wosai.bright.service.SysOperatingService;
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
 * Created by Administrator on 2017/3/23 0023.
 */
@Controller
@RequestMapping("/web")
public class SysOperatingController {

    private static final transient Logger log = LoggerFactory.getLogger(SysOperatingController.class);

    @Autowired
    private SysOperatingService sysOperatingService;

    /**
     * 查看操作日志
     * @param request
     * @param response
     * @param page 页码
     * @param rows 每页个数
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "sysOperatingLog")
    public Object findSysOperatingLog(
            HttpServletRequest request ,
            HttpServletResponse response ,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows,
            @RequestParam(required = false) String content){
        PageHelper.startPage(page, rows);
        Map<String , Object> condition = JSON.parseObject(content);
        final List<SysOperatingLog> list = sysOperatingService.findSysOperatingLog(condition);

        final PageInfo pageInfo = new PageInfo(list);

        return new HashedMap() {{
            put("object", list);
            put("totalRec",pageInfo.getTotal());
            put("curRec",pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

}
