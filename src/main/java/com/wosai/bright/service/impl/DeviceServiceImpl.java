package com.wosai.bright.service.impl;

import com.wosai.bright.mapper.DeviceMapper;
import com.wosai.bright.model.Device;
import com.wosai.bright.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@Service("deviceService")
public class DeviceServiceImpl extends BaseService<Device> implements DeviceService{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<Map<String, Object>> findDevice(Map<String, Object> condition) {
        return deviceMapper.findDevice(condition);
    }

    @Override
    public List<String> findDeviceModelCode() {
        return deviceMapper.findDeviceModelCode();
    }

    @Override
    public Device getDevice(Short deviceId){
        return deviceMapper.getDevice(deviceId);
    }

    @Override
    public Device getDeviceByUcode(String ucode){
        return deviceMapper.getDeviceByUcode(ucode);
    };
}
