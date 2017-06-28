package com.wosai.bright.mapper;

import com.wosai.bright.model.DeviceWarningLever;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public interface DeviceWarningLeverMapper extends Mapper<DeviceWarningLever> , MySqlMapper<DeviceWarningLever> {
    DeviceWarningLever getLevel(Short menuId);
}
