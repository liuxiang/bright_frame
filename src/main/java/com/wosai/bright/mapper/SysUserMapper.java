package com.wosai.bright.mapper;

import com.wosai.bright.model.SysUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface SysUserMapper extends Mapper<SysUser>, MySqlMapper<SysUser> {
}