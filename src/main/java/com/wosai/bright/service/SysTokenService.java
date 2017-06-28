package com.wosai.bright.service;

import com.wosai.bright.model.SysToken;
import com.wosai.bright.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public interface SysTokenService extends IService<SysToken>{

    public String saveSysToken(Short id);

    SysUser getUser(HttpServletRequest request);
}
