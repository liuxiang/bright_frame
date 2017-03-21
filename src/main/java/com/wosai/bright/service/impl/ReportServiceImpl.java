package com.wosai.bright.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wosai.bright.mapper.DevicePropertyLogMapper;
import com.wosai.bright.mapper.DevicePropertyMapper;
import com.wosai.bright.model.Device;
import com.wosai.bright.model.DeviceProperty;
import com.wosai.bright.model.DevicePropertyLog;
import com.wosai.bright.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
@Service("reportService")
public class ReportServiceImpl extends BaseService<Device> implements ReportService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DevicePropertyMapper devicePropertyMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DevicePropertyLogMapper devicePropertyLogMapper;

    @Override
    public void report(String device_name, String device_ip, String device_info) {

        // 更新当前设备状态
        Example example_Device = new Example(Device.class);
        Example.Criteria criteria_Device = example_Device.createCriteria();
        criteria_Device.andEqualTo("ip", device_ip);// 使用ip寻找设备
        Device device = (Device) selectByExample(example_Device).get(0);

        Example example_DeviceProperty = new Example(DeviceProperty.class);
        Example.Criteria criteria_DeviceProperty = example_DeviceProperty.createCriteria();
        criteria_DeviceProperty.andEqualTo("deviceId", device.getId());
        List devicePropertyList = devicePropertyMapper.selectByExample(example_DeviceProperty);

        Map<String, Object> devicePropertyMap = new HashMap();
        for (int i = 0; i < devicePropertyList.size(); i++) {
            DeviceProperty deviceProperty = (DeviceProperty) devicePropertyList.get(i);
            devicePropertyMap.put(deviceProperty.getKey(), deviceProperty);
        }

        JSONObject jsonObject = JSON.parseObject(device_info);
        for (Map.Entry<String, Object> e : jsonObject.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
            DeviceProperty deviceProperty = (DeviceProperty) devicePropertyMap.get(e.getKey());
            if (deviceProperty != null) {
                deviceProperty.setValue(String.valueOf(e.getValue()));
                deviceProperty.setUpdateTime(new Date());
                devicePropertyMapper.updateByPrimaryKey(deviceProperty);
            } else {
                deviceProperty = new DeviceProperty();
                deviceProperty.setDeviceId(device.getId());
                deviceProperty.setKey(e.getKey());
                deviceProperty.setValue(String.valueOf(e.getValue()));
                deviceProperty.setUpdateTime(new Date());
                devicePropertyMapper.insert(deviceProperty);
            }

            // 记录设备历史,TODO 注意数据过滤
            DevicePropertyLog devicePropertyLog = new DevicePropertyLog();
            devicePropertyLog.setDeviceId(device.getId());
            devicePropertyLog.setKey(e.getKey());
            devicePropertyLog.setValue(String.valueOf(e.getValue()));
            devicePropertyLog.setUpdateTime(new Date());
            devicePropertyLogMapper.insert(devicePropertyLog);
        }
    }
}
