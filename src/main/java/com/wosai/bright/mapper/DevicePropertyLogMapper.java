package com.wosai.bright.mapper;

import com.wosai.bright.model.DevicePropertyLog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface DevicePropertyLogMapper extends Mapper<DevicePropertyLog>, MySqlMapper<DevicePropertyLog> {
    List<Map<String,Object>> findDevicePropertyLogList(Map<String, Object> condition);
}