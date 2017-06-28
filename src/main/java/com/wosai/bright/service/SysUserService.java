package com.wosai.bright.service;

import com.wosai.bright.model.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public interface SysUserService extends IService<SysUser>{

    List<SysUser> findUser(Map<String, Object> condition);

    SysUser getUser(Short userId);

    /**
     * 验证原密码（app修改密码）
     * @return
     */
    int checkPassword(Integer userId,String password);

    /**
     * 保存头像
     * @param file 头像文件
     * @return
     */
    Object saveProfile(HttpServletRequest request,Integer id,MultipartFile file);


}
