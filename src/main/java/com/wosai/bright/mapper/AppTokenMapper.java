package com.wosai.bright.mapper;

import com.wosai.bright.model.AppToken;
import com.wosai.bright.model.AppUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AppTokenMapper extends Mapper<AppToken>, MySqlMapper<AppToken> {

    AppToken getToken(String token);

    AppUser getUser(String token);
}