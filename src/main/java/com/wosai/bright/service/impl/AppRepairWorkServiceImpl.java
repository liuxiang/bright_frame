package com.wosai.bright.service.impl;

import com.wosai.bright.common.Constants;
import com.wosai.bright.common.utils.FileUtil_ws;
import com.wosai.bright.mapper.AppRepairWorkLogMapper;
import com.wosai.bright.mapper.AppRepairWorkMapper;
import com.wosai.bright.model.AppRepairWork;
import com.wosai.bright.model.AppRepairWorkLog;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.AppRepairWorkService;
import com.wosai.bright.service.SysTokenService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("AppRepairWorkService")
public class AppRepairWorkServiceImpl extends BaseService<AppRepairWork> implements AppRepairWorkService {

    @Autowired
    AppRepairWorkMapper appRepairWorkMapper;

    @Autowired
    AppRepairWorkLogMapper appRepairWorkLogMapper;

    @Autowired
    SysTokenService sysTokenService;

    /**
     * 报修工单-变更
     *
     * @param request
     * @param appRepairWork
     * @param file1
     * @param file2
     * @param file3
     * @param file4
     * @return
     */
    @Override
    public Object appRepairWork_save(HttpServletRequest request, AppRepairWork appRepairWork
            , @RequestParam(required = false, value = "file1") MultipartFile file1
            , @RequestParam(required = false, value = "file2") MultipartFile file2
            , @RequestParam(required = false, value = "file3") MultipartFile file3
            , @RequestParam(required = false, value = "file4") MultipartFile file4) {

        SysUser sysUser = sysTokenService.getUser(request);
        if (appRepairWork.getId() != null) {
//            AppRepairWork appRepairWork1 = appRepairWorkMapper.selectByPrimaryKey(appRepairWork.getId());// 获取新对象,避免对象覆盖
//            // 参与更新的列 begin
//            if (appRepairWork.getDeviceId() != null) {
//                appRepairWork1.setDeviceId(appRepairWork.getDeviceId());
//            }
//            if (appRepairWork.getHandleUserId() != null) {
//                appRepairWork1.setHandleUserId(appRepairWork.getHandleUserId());
//            }
//            if (appRepairWork.getStatus() != null) {
//                appRepairWork1.setStatus(appRepairWork.getStatus());
//            }
//            // 参与更新的列 end
//
//            appRepairWork1.setUpdateTime(new Date());
//            // 完成订单标记解决人
//            if (appRepairWork1.getStatus() == Constants.REPAIR_WORK_STATUS_SUCCESS) {
//                // appRepairWork.setHandleUserId(sysUser.getId());// 当前指派
//                appRepairWork1.setSolveUserId(sysUser.getId());// 解决人
//            }
//            appRepairWorkMapper.updateByPrimaryKey(appRepairWork1);

            appRepairWork.setUpdateTime(new Date());
            // 完成订单标记解决人
            if (appRepairWork.getStatus() == Constants.REPAIR_WORK_STATUS_SUCCESS) {
                // appRepairWork.setHandleUserId(sysUser.getId());// 当前指派
                appRepairWork.setSolveUserId(sysUser.getId());// 解决人
            }
            appRepairWorkMapper.updateByPrimaryKey(appRepairWork);
        } else {

            // 上传文件
            try {
                appRepairWork.setImg1(FileUtil_ws.uploadFileCheckImgAndLocal(request, file1, "image/repairWork"));
                appRepairWork.setImg2(FileUtil_ws.uploadFileCheckImgAndLocal(request, file2, "image/repairWork"));
                appRepairWork.setImg3(FileUtil_ws.uploadFileCheckImgAndLocal(request, file3, "image/repairWork"));
                appRepairWork.setImg4(FileUtil_ws.uploadFileCheckImgAndLocal(request, file4, "image/repairWork"));
            } catch (final Exception e) {
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", e.getMessage());
                }};
            }

            String code = "work_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + (int) (1000 + Math.random() * (9999 - 1000 + 1));
            appRepairWork.setCode(code);
            appRepairWork.setSponsorUserId(sysUser.getId());
            appRepairWork.setStatus(Constants.REPAIR_WORK_STATUS_WAIT);
            appRepairWork.setCreateTime(new Date());
            appRepairWorkMapper.updateByPrimaryKey(appRepairWork);
            appRepairWorkMapper.insert(appRepairWork);
        }

        // 记录报修工单日志
        AppRepairWorkLog appRepairWorkLog = new AppRepairWorkLog();
        appRepairWorkLog.setWorkCode(appRepairWork.getCode());
        appRepairWorkLog.setWorkStatus(appRepairWork.getStatus());
        appRepairWorkLog.setWorkHandleUserId(appRepairWork.getHandleUserId());
        appRepairWorkLog.setOperatorId(sysUser.getId());
        appRepairWorkLog.setCreateTime(new Date());
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
}
