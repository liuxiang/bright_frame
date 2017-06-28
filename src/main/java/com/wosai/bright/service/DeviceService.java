package com.wosai.bright.service;

import com.wosai.bright.model.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public interface DeviceService extends IService<Device>{
    List<Map<String, Object>> findDevice(Map<String, Object> condition);

    List<String> findDeviceModelCode();

    Device getDevice(Short deviceId);

    Device getDeviceByUcode(String ucode);
}
