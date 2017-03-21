package com.wosai.bright.service;

import com.wosai.bright.model.Device;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public interface ReportService extends IService<Device> {

    /**
     * 采集接口
     * @param device_name
     * @param device_ip
     * @param device_info
     */
    void report(String device_name, String device_ip,String device_info);
}
