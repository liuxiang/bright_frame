package com.wosai.bright.mapper;

import com.wosai.bright.model.Device;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface DeviceMapper extends Mapper<Device>, MySqlMapper<Device> {
    List<Map<String, Object>> findDevice(Map<String, Object> condition);

    List<String> findDeviceModelCode();

    Device getDevice(Short deviceId);

    List<Device> findOffLineDeviceId();

    Device getDeviceByUcode(String ucode);
}