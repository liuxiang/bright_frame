package com.wosai.bright.service.impl;

import com.wosai.bright.mapper.SysOperatingLogMapper;
import com.wosai.bright.model.SysOperatingLog;
import com.wosai.bright.service.SysOperatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
@Service("sysOperatingService")
public class SysOperatingServiceImpl extends BaseService<SysOperatingLog> implements SysOperatingService{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysOperatingLogMapper sysOperatingLogMapper;

    @Override
    public List<SysOperatingLog> findSysOperatingLog(Map<String, Object> condition) {
        List<SysOperatingLog> list = sysOperatingLogMapper.findSysOperatingLog(condition);
        return list;
    }

    @Override
    public void saveSysOperatingLog(String code, Short id, String content) {
        SysOperatingLog log = new SysOperatingLog();
        log.setUserId(id);
        log.setContent(content);
        log.setCreateTime(new Date());
        log.setOperatingCode(code);
        save(log);
    }

}
