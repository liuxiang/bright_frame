package com.wosai.bright.mapper;

import com.wosai.bright.model.AppDailyInspect;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface AppDailyInspectMapper extends Mapper<AppDailyInspect>, MySqlMapper<AppDailyInspect> {

    /**
     * 查询设备巡检信息
     *
     * @param condition
     * @return
     */
    List<Map> selectDeviceDailyInspect(Map<String, Object> condition);
    List<Map> selectDeviceDailyInspect(AppDailyInspect AppDailyInspect);
}