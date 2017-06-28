package com.wosai.bright.mapper;

import com.wosai.bright.model.DeviceRepair;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface DeviceRepairMapper extends Mapper<DeviceRepair>, MySqlMapper<DeviceRepair> {
    List<DeviceRepair> findDeviceRepair(Map<String, Object> condition);
    DeviceRepair getDeviceRepair(Map<String,Object> condition);
}