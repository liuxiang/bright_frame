package com.wosai.bright.mapper;

import com.wosai.bright.model.SysToken;
import com.wosai.bright.model.SysUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface SysTokenMapper extends MySqlMapper<SysToken>, Mapper<SysToken> {
    SysToken getToken(String token);

    SysUser getUser(String token);
}