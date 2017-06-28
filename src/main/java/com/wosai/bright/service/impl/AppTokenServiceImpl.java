package com.wosai.bright.service.impl;

import com.wosai.bright.common.utils.RandomUtils;
import com.wosai.bright.common.utils.TokenUtils;
import com.wosai.bright.mapper.AppTokenMapper;
import com.wosai.bright.model.AppToken;
import com.wosai.bright.model.AppUser;
import com.wosai.bright.service.AppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Service("AppTokenService")
public class AppTokenServiceImpl extends BaseService<AppToken> implements AppTokenService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    AppTokenMapper appTokenMapper;

    @Override
    public String saveAppToken(Short id) {
        String tokenStr = TokenUtils.SHA256(new Date().getTime() + RandomUtils.getRandomStringALL(6));
        Date createDate = new Date();
        Date expireDate = new Date(createDate.getTime() + 60 * 60 * 1000);

        AppToken token = new AppToken();
        token.setUserId(id);
        token.setToken(tokenStr);
        token.setCreateTime(createDate);
        token.setExpireTime(expireDate);
        save(token);
        return tokenStr;
    }

    @Override
    public AppUser getUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        AppUser user = appTokenMapper.getUser(token);
        return user;
    }
}
