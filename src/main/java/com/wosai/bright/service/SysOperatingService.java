package com.wosai.bright.service;

import com.wosai.bright.model.SysOperatingLog;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public interface SysOperatingService extends IService<SysOperatingLog> {

    List findSysOperatingLog(Map<String , Object> condition);

    /**
     * 记录操作日志
     * @param code 操作code
     * @param id userid
     * @param content 文字描述
     */
    void saveSysOperatingLog(String code, Short id, String content);
}
