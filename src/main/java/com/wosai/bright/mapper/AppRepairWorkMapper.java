package com.wosai.bright.mapper;

import com.wosai.bright.model.AppRepairWork;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface AppRepairWorkMapper extends Mapper<AppRepairWork>, MySqlMapper<AppRepairWork> {

    /**
     * 查询报修工单
     *
     * @param condition
     * @return
     */
    List<Map> selectRepairWork(Map<String, Object> condition);
}