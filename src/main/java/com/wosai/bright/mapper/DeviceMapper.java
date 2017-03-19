package com.wosai.bright.mapper;

import com.wosai.bright.model.Device;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DeviceMapper extends Mapper<Device>, MySqlMapper<Device> {
}