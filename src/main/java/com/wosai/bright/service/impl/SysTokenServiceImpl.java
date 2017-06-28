package com.wosai.bright.service.impl;

import com.wosai.bright.common.utils.RandomUtils;
import com.wosai.bright.common.utils.TokenUtils;
import com.wosai.bright.mapper.SysTokenMapper;
import com.wosai.bright.model.SysToken;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Service("sysTokenService")
public class SysTokenServiceImpl extends BaseService<SysToken> implements SysTokenService{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SysTokenMapper sysTokenMapper;

    @Override
    public String saveSysToken(Short id) {
        String tokenStr = TokenUtils.SHA256(new Date().getTime()+ RandomUtils.getRandomStringALL(6));
        Date createDate = new Date();
        Date expireDate = new Date(createDate.getTime() + 60 * 60 * 1000);

        SysToken token = new SysToken();
        token.setUserId(id);
        token.setToken(tokenStr);
        token.setCreateTime(createDate);
        token.setExpireTime(expireDate);
        save(token);
        return tokenStr;
    }

    @Override
    public SysUser getUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        SysUser user = sysTokenMapper.getUser(token);
        return user;
    }
}
