package com.wosai.bright.controller_web.device_warning;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.DeviceWarningRuleMapper;
import com.wosai.bright.model.DeviceWarningRule;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
@Controller
@RequestMapping("/web")
public class DeviceWarningRuleController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceWarningRuleMapper deviceWarningRuleMapper;

    private static final transient Logger log = LoggerFactory.getLogger(DeviceWarningRuleController.class);

    /**
     * 获取报警预设规则列表
     * @param request
     * @param response
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET , value = {"/deviceWarningRule"})
    public Object findDeviceWarningRuleList(
            HttpServletRequest request ,
            HttpServletResponse response ,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows){
        PageHelper.startPage(page, rows);
        final List<DeviceWarningRule> list = deviceWarningRuleMapper.selectAll();

        final PageInfo pageInfo = new PageInfo(list);

        return new HashedMap() {{
            put("object", list);
            put("totalRec",pageInfo.getTotal());
            put("curRec",pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};

    }

    /**
     * 创建、更新报警预设规则
     * @param request
     * @param response
     * @param id
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = {"/deviceWarningRule"})
    public  Object updateDeviceWarningRule(
            HttpServletRequest request ,
            HttpServletResponse response,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = true) String content){
        DeviceWarningRule rule = new DeviceWarningRule();
        if (id != null){
            rule = deviceWarningRuleMapper.selectByPrimaryKey(id.shortValue());
            if (rule == null || rule.getId() == null){
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "参数错误");
                }};
            }
        }
        Map<String , Object> condition = (Map<String, Object>) JSON.parse(content);
        rule.setModelCode((String) condition.get("modelCode"));
        rule.setModelPropertyKey((String) condition.get("modelPropertyKey"));
        rule.setTargetValue((String) condition.get("targetValue"));
        rule.setUpperLimit(((Integer) condition.get("upperLimit")).shortValue());
        rule.setLowerLimit(((Integer) condition.get("lowerLimit")).shortValue());
        rule.setFrequency(((Integer) condition.get("frequency")).shortValue());
        rule.setIsaccum(((Integer) condition.get("isaccum")).shortValue());
        if (id != null){
            deviceWarningRuleMapper.updateByPrimaryKey(rule);
        }else {
            deviceWarningRuleMapper.insert(rule);
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value= {"/deviceWarningRule/{id}"})
    public Object deleteDeviceWarningRule(HttpServletResponse respone,
                                          HttpServletRequest request,
                                          @PathVariable Integer id){
        DeviceWarningRule warningRule = deviceWarningRuleMapper.selectByPrimaryKey(id.shortValue());
        if(warningRule == null){
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "信息错误，没有找到该报警规则");
            }};
        }
        deviceWarningRuleMapper.delete(warningRule);

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    };
}
