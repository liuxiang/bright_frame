package com.wosai.bright.mapper;

import com.wosai.bright.model.SysMenuData;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface SysMenuDataMapper extends Mapper<SysMenuData>, MySqlMapper<SysMenuData> {

    List<Map<String,Object>> selectAllInfo();

    SysMenuData getMenuData(Map<String, Object> map);
}