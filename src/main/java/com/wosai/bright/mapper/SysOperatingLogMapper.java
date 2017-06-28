package com.wosai.bright.mapper;

import com.wosai.bright.model.SysOperatingLog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface SysOperatingLogMapper extends Mapper<SysOperatingLog>, MySqlMapper<SysOperatingLog> {
    List<SysOperatingLog> findSysOperatingLog(Map<String, Object> condition);
}