package com.wosai.bright.mapper;

import com.wosai.bright.model.AppUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AppUserMapper extends Mapper<AppUser>, MySqlMapper<AppUser> {
}