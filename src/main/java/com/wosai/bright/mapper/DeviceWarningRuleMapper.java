package com.wosai.bright.mapper;

import com.wosai.bright.model.DeviceWarningRule;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Map;

public interface DeviceWarningRuleMapper extends Mapper<DeviceWarningRule>, MySqlMapper<DeviceWarningRule> {

    DeviceWarningRule getRule(Map<String, Object> condition);
}