package com.wosai.bright.mapper;

import com.wosai.bright.model.DeviceWarning;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface DeviceWarningMapper extends Mapper<DeviceWarning>, MySqlMapper<DeviceWarning> {

    List findDeviceWarningLog(Map<String,Object> condition);

    DeviceWarning getDeviceWarningLateLy(Map<String, Object> condition);

    DeviceWarning getOffLineDeviceWarningLately(Short deviceId);
}