package com.wosai.bright.controller_api.monitor;

import com.alibaba.fastjson.JSON;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.SysMenuDataMapper;
import com.wosai.bright.model.SysMenuData;
import com.wosai.bright.service.ReportService;
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
 * Created by Administrator on 2017/4/11 0011.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/API/monitor")
public class ReportMenuDataController {

    private static final transient Logger log = LoggerFactory.getLogger(ReportMenuDataController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private SysMenuDataMapper sysMenuDataMapper;

    /**
     * 上报大屏数据内容
     * @param request
     * @param response
     * @param content json串
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "report/menuData")
    public Object reportMenuData(
            HttpServletRequest request ,
            HttpServletResponse response ,
            @RequestParam(required = false)String content){
        List<SysMenuData> list = JSON.parseArray(content , SysMenuData.class);
        for (SysMenuData data : list){
            Map<String , Object> condition = new HashedMap();
            condition.put("menuId",data.getMenuId());
            condition.put("key",data.getKey());
            SysMenuData oldData = sysMenuDataMapper.getMenuData(condition);
            if (oldData == null || oldData.getId() == null){
                SysMenuData newData = new SysMenuData();
                newData.setKey(data.getKey());
                newData.setMenuId(data.getMenuId());
                newData.setValue(data.getValue());
                sysMenuDataMapper.insert(newData);
            }else {
                oldData.setValue(data.getValue());
                sysMenuDataMapper.updateByPrimaryKey(oldData);
            }
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }




}
