package com.wosai.bright.controller_web.app_repair;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.AppRepairWorkLogMapper;
import com.wosai.bright.mapper.AppRepairWorkMapper;
import com.wosai.bright.model.AppRepairWork;
import com.wosai.bright.model.AppRepairWorkLog;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.AppRepairWorkService;
import com.wosai.bright.service.SysTokenService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 报修工单
 * Created by Administrator on 2017/5/4 0004.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web/repair")
public class AppRepairController {

    @Autowired
    AppRepairWorkMapper appRepairWorkMapper;

    @Autowired
    AppRepairWorkService appRepairWorkService;

    @Autowired
    AppRepairWorkLogMapper appRepairWorkLogMapper;

    @Autowired
    SysTokenService sysTokenService;

    /**
     * 报修工单_查询
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "")
    public Object appRepairWork(HttpServletRequest request, AppRepairWork appRepairWork, @RequestParam(required = false) String date
            , @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "1000") Integer rows) {

        SysUser sysUser = sysTokenService.getUser(request);
        Map<String, Object> condition = new HashedMap();
        condition.put("status", appRepairWork.getStatus());
        condition.put("id", appRepairWork.getId());
        condition.put("code", appRepairWork.getCode());
        condition.put("deviceId", appRepairWork.getDeviceId());
        condition.put("sponsorUserId", appRepairWork.getSponsorUserId());
        condition.put("handleUserId", appRepairWork.getHandleUserId());
        condition.put("solveUserId", appRepairWork.getSolveUserId());
        condition.put("status", appRepairWork.getStatus());

        condition.put("sponsorUserName", request.getParameter("sponsorUserName"));
        condition.put("menuId", request.getParameter("menuId"));
        condition.put("deviceName", request.getParameter("deviceName"));
        condition.put("modelCode", request.getParameter("modelCode"));
        condition.put("beginTime", request.getParameter("beginTime"));
        condition.put("endTime", request.getParameter("endTime"));

        // 非管理原因仅能看到自己相关的工单
        if (sysUser.getType() != Constants.SYS_USER_TYPE_MANAGE_APP
                || sysUser.getType() != Constants.SYS_USER_TYPE_MANAGE) {
            condition.put("userId", sysUser.getId());
        }

        PageHelper.startPage(page, rows);
        final List<Map> appRepairWorks = appRepairWorkMapper.selectRepairWork(condition);
        final PageInfo pageInfo = new PageInfo(appRepairWorks);

        return new HashedMap() {{
            put("object", appRepairWorks);
            put("pageInfo", pageInfo);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 报修工单_新增/修改
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "save")
    public Object appRepairWork_save(HttpServletRequest request, AppRepairWork appRepairWork
            , @RequestParam(required = false, value = "file1") MultipartFile file1
            , @RequestParam(required = false, value = "file2") MultipartFile file2
            , @RequestParam(required = false, value = "file3") MultipartFile file3
            , @RequestParam(required = false, value = "file4") MultipartFile file4) {
        // Service 受事务保护
        return appRepairWorkService.appRepairWork_save(request, appRepairWork, file1, file2, file3, file4);
    }

    /**
     * 更新工单_设备
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save/device")
    public Object appRepairWork_save_device(HttpServletRequest request, AppRepairWork appRepairWork) {
        AppRepairWork appRepairWork1 = appRepairWorkMapper.selectByPrimaryKey(appRepairWork.getId());
        appRepairWork1.setDeviceId(appRepairWork.getDeviceId());// 更新当前指派人
        return appRepairWork_save(request, appRepairWork1, null, null, null, null);
    }

    /**
     * 报修工单_指派
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save/assign")
    public Object appRepairWork_save_assign(HttpServletRequest request, AppRepairWork appRepairWork) {
        AppRepairWork appRepairWork1 = appRepairWorkMapper.selectByPrimaryKey(appRepairWork.getId());
        appRepairWork1.setHandleUserId(appRepairWork.getHandleUserId());// 更新当前指派人
        return appRepairWork_save(request, appRepairWork1, null, null, null, null);
    }

    /**
     * 报修工单_开始处理
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save/begin")
    public Object appRepairWork_save_begin(HttpServletRequest request, AppRepairWork appRepairWork) {
        AppRepairWork appRepairWork1 = appRepairWorkMapper.selectByPrimaryKey(appRepairWork.getId());
        appRepairWork1.setStatus(Constants.REPAIR_WORK_STATUS_BEGIN);// 更新状态
        appRepairWork1.setHandleUserId(sysTokenService.getUser(request).getId());// 更新处理人
        return appRepairWork_save(request, appRepairWork1, null, null, null, null);
    }

    /**
     * 报修工单_处理完成
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save/success")
    public Object appRepairWork_save_success(HttpServletRequest request, AppRepairWork appRepairWork) {
        AppRepairWork appRepairWork1 = appRepairWorkMapper.selectByPrimaryKey(appRepairWork.getId());
        appRepairWork1.setStatus(Constants.REPAIR_WORK_STATUS_SUCCESS);// 更新状态
        return appRepairWork_save(request, appRepairWork1, null, null, null, null);
    }

    /**
     * 报修工单_删除
     *
     * @param appRepairWork
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "remove")
    public Object appRepairWork_remove(AppRepairWork appRepairWork) {
        appRepairWorkMapper.deleteByPrimaryKey(appRepairWork);// 删除数据

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 报修工单日志_查询
     *
     * @param appRepairWorkLog
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "repairWorkLog")
    public Object appRepairWorkLog(AppRepairWorkLog appRepairWorkLog) {
        final List<AppRepairWorkLog> appRepairWorkLogs = appRepairWorkLogMapper.select(appRepairWorkLog);// 删除数据

        return new HashedMap() {{
            put("object", appRepairWorkLogs);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


    /**
     * 报修工单日志_新增/修改
     *
     * @param appRepairWorkLog
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "repairWorkLog/save")
    public Object appRepairWorkLog_save(AppRepairWorkLog appRepairWorkLog) {
        if (appRepairWorkLog.getId() != null) {
            appRepairWorkLogMapper.updateByPrimaryKey(appRepairWorkLog);
        } else {
            appRepairWorkLogMapper.insert(appRepairWorkLog);
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 报修工单日志_删除
     *
     * @param appRepairWorkLog
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "repairWorkLog/remove")
    public Object appRepairWorkLog_remove(AppRepairWorkLog appRepairWorkLog) {
        appRepairWorkLogMapper.deleteByPrimaryKey(appRepairWorkLog);// 删除数据

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }
}
