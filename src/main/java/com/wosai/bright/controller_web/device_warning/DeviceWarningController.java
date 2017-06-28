package com.wosai.bright.controller_web.device_warning;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.DeviceWarningMapper;
import com.wosai.bright.mapper.SysUserMapper;
import com.wosai.bright.model.DeviceWarning;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysTokenService;
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
 * Created by Administrator on 2017/3/23 0023.
 */
@Controller
@RequestMapping("/web")
public class DeviceWarningController {

    private static final transient Logger log = LoggerFactory.getLogger(DeviceWarningController.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceWarningMapper deviceWarningMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysUserMapper sysUserMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysTokenService sysTokenService;

    /**
     * 查看报警日志记录(status 状态，0正常，1异常，2误报)
     * @param request
     * @param response
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"deviceWarning"})
    @ResponseBody
    public Map<String , Object> findDeviceWarningLog(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) final Integer page,
            @RequestParam(required = false) Integer rows){
        Map<String , Object> condition = (Map<String, Object>) JSON.parse(content);
        PageHelper.startPage(page, rows);
        final List<Map<String,Object>> list = deviceWarningMapper.findDeviceWarningLog(condition);
//        for (int i = 0; i<list.size(); i++){
//            Map<String,Object> dtoMap = list.get(i);
//            BigDecimal bigDecimal = (BigDecimal) dtoMap.get("userId");
//            if (bigDecimal != null){
//                Short userId = bigDecimal.shortValue();
//                if (userId != null) {
//                    SysUser user = sysUserMapper.selectByPrimaryKey(userId);
//                    dtoMap.put("userName", user.getName());
//                }
//            }
//        }

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
     * 处理报警日志
     * @param request
     * @param response
     * @param deviceWarningId 报警日志id
     * @param status 状态，0正常，1异常，2误报
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = {"deviceWarning/{deviceWarningId}/{status}"})
    @ResponseBody
    public Map<String , Object> updateDeviceWarning(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer deviceWarningId,
            @PathVariable Integer status){
        DeviceWarning deviceWarning = deviceWarningMapper.selectByPrimaryKey(deviceWarningId.shortValue());
        if (deviceWarning == null || deviceWarning.getId() == null){
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "参数错误");
            }};
        }
        deviceWarning.setStatus(status.shortValue());

        SysUser sysUser = sysTokenService.getUser(request);
        if (sysUser == null || sysUser.getId() == null){
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "参数错误");
            }};
        }
        deviceWarning.setUserId(sysUser.getId());
        deviceWarningMapper.updateByPrimaryKey(deviceWarning);

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }



}
