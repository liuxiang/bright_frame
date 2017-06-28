package com.wosai.bright.service;

import com.wosai.bright.model.AppToken;
import com.wosai.bright.model.AppUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public interface AppTokenService extends IService<AppToken>{

    public String saveAppToken(Short id);

    AppUser getUser(HttpServletRequest request);
}
