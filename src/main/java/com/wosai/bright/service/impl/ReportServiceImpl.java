package com.wosai.bright.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.*;
import com.wosai.bright.model.*;
import com.wosai.bright.service.ReportService;
import com.wosai.bright.websocket_tomcat.MyWebSocket;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

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

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceWarningRuleMapper deviceWarningRuleMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceWarningMapper deviceWarningMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceWarningLeverMapper deviceWarningLeverMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public void report(String device_name, String device_ip, String device_info, String ucode) {

        Example example_Device = new Example(Device.class);
        Example.Criteria criteria_Device = example_Device.createCriteria();
        if (ucode != null) {
            criteria_Device.andEqualTo("ucode", ucode);// 使用ip寻找设备
        } else {
            criteria_Device.andEqualTo("ip", device_ip);// 使用ip寻找设备
        }
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

        JSONObject device_info_json = JSON.parseObject(device_info);

        // TODO 报警规则匹配（如因报警，加入报警日志）
        warningRule(device, device_info_json);

        for (Map.Entry<String, Object> e : device_info_json.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
            DeviceProperty deviceProperty = (DeviceProperty) devicePropertyMap.get(e.getKey());
            if (deviceProperty != null) {
                deviceProperty.setValue(String.valueOf(e.getValue()));
                deviceProperty.setUpdateTime(new Date());
                devicePropertyMapper.updateByPrimaryKey(deviceProperty);// 更新当前设备状态
            } else {
                deviceProperty = new DeviceProperty();
                deviceProperty.setDeviceId(device.getId());
                deviceProperty.setKey(e.getKey());
                deviceProperty.setValue(String.valueOf(e.getValue()));
                deviceProperty.setUpdateTime(new Date());
                devicePropertyMapper.insert(deviceProperty);// 首次上报,添加设备状态
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

    /**
     * 报警规则匹配
     *
     * @param device
     * @param device_info
     */
    public void warningRule(Device device, JSONObject device_info) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CopyOnWriteArraySet<MyWebSocket> webSocketSet = MyWebSocket.getWebSocketSet();
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(device.getName() + ":" + device_info.toJSONString());
                for (Map.Entry<String, Object> e : device_info.entrySet()) {
                    Map<String, Object> condition = new HashedMap();
                    condition.put("modelCode", device.getModelCode());
                    condition.put("key", e.getKey());
                    //获取报警规则
                    DeviceWarningRule rule = deviceWarningRuleMapper.getRule(condition);
                    if (rule != null) {
                        Integer status = Constants.NO;
                        if (rule.getTargetValue() != null && rule.getTargetValue().equals(e.getValue())) {
                            //直接值报警
                            status = Constants.YES;
                        } else if (rule.getLowerLimit() != null && rule.getLowerLimit() >= Short.parseShort((String) e.getValue())) {
                            //最低值报警
                            status = Constants.YES;
                        } else if (rule.getUpperLimit() != null && rule.getUpperLimit() <= Short.parseShort((String) e.getValue())) {
                            //最高值报警
                            status = Constants.YES;
                        }
                        //判断是否报警
                        if (status == Constants.YES) {
                            condition.clear();
                            condition.put("deviceId", device.getId());
                            condition.put("ruleId", rule.getId());
                            DeviceWarning deviceWarning = deviceWarningMapper.getDeviceWarningLateLy(condition);
                            if (deviceWarning != null && (deviceWarning.getStatus() == Constants.DEVICE_WARNING_STATUS_NORMAL || deviceWarning.getStatus() == Constants.DEVICE_WARNING_STATUS_WARNING) && rule.getIsaccum() == Constants.YES && sdf.format(new Date()).equals(sdf.format(deviceWarning.getUpdateTime()))) {
                                //最近一次报警日志不为空，且状态为正常或者报警，且累计为yes，且日期为今天
                                //更新sum+1，重置报警状态为报警
                                deviceWarning.setStatus((short) Constants.DEVICE_WARNING_STATUS_WARNING);
                                Integer sum = deviceWarning.getWarningSum().intValue();
                                sum = sum + 1;
                                deviceWarning.setWarningSum(sum.shortValue());
                                deviceWarning.setUpdateTime(new Date());
                                deviceWarningMapper.updateByPrimaryKey(deviceWarning);
                                item.sendMessage("[warning]" + device.getName() + ":" + "'key':" + e.getKey() + ",'value':" + e.getValue());
                            } else if (deviceWarning != null && deviceWarning.getStatus() == Constants.DEVICE_WARNING_STATUS_MISINFORMATION && rule.getIsaccum() == Constants.YES && sdf.format(new Date()).equals(sdf.format(deviceWarning.getUpdateTime()))) {
                                //最近一次报警日志不为空，且状态为误报，且累计为yes，且日期为今天
                                //更新sum+1
                                Integer sum = deviceWarning.getWarningSum().intValue();
                                sum = sum + 1;
                                deviceWarning.setWarningSum(sum.shortValue());
                                deviceWarning.setUpdateTime(new Date());
                                deviceWarningMapper.updateByPrimaryKey(deviceWarning);
                            } else {
                                //其他所有情况，都新建一条新的报警日志
                                DeviceWarning newDeviceWarning = new DeviceWarning();
                                newDeviceWarning.setCreateTime(new Date());
                                newDeviceWarning.setUpdateTime(newDeviceWarning.getCreateTime());
                                newDeviceWarning.setRuleId(rule.getId());
                                newDeviceWarning.setDeviceId(device.getId());
                                newDeviceWarning.setStatus((short) Constants.DEVICE_WARNING_STATUS_WARNING);
                                newDeviceWarning.setDevicePropertyKey(e.getKey());
                                newDeviceWarning.setValue((String) e.getValue());
                                newDeviceWarning.setMenuId(device.getMenuId());
                                newDeviceWarning.setWarningSum(new Integer(1).shortValue());
                                DeviceWarningLever lever = deviceWarningLeverMapper.getLevel(device.getMenuId());
                                if (lever != null) {
                                    newDeviceWarning.setLever(lever.getLever());
                                }
                                deviceWarningMapper.insert(newDeviceWarning);
                                item.sendMessage("[warning]" + device.getName() + ":" + "'key':" + e.getKey() + ",'value':" + e.getValue());
                            }

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @Override
    public void saveOffLineDeviceWarning() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CopyOnWriteArraySet<MyWebSocket> webSocketSet = MyWebSocket.getWebSocketSet();
        for (MyWebSocket item : webSocketSet) {
            try {
                List<Device> deviceIdList = deviceMapper.findOffLineDeviceId();
                for (Device device : deviceIdList) {
                    DeviceWarning deviceWarning = deviceWarningMapper.getOffLineDeviceWarningLately(device.getId());
                    if (deviceWarning == null) {
                        DeviceWarning newDeviceWarning = new DeviceWarning();
                        newDeviceWarning.setDeviceId(device.getId());
                        newDeviceWarning.setStatus(Constants.DEVICE_WARNING_STATUS_OFFLINE);
                        newDeviceWarning.setMenuId(device.getMenuId());
                        newDeviceWarning.setCreateTime(new Date());
                        newDeviceWarning.setUpdateTime(new Date());
                        newDeviceWarning.setWarningSum(new Integer(1).shortValue());
                        deviceWarningMapper.insert(newDeviceWarning);
                    } else {
                        deviceWarning.setUpdateTime(new Date());
                        int sum = deviceWarning.getWarningSum() + 1;
                        deviceWarning.setWarningSum((short) sum);
                        deviceWarningMapper.updateByPrimaryKey(deviceWarning);
                    }
                    item.sendMessage("[warning]Offline");
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }

}
