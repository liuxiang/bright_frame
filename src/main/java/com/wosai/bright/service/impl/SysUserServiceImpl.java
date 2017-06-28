package com.wosai.bright.service.impl;

import com.wosai.bright.common.Constants;
import com.wosai.bright.common.utils.FileUtil_ws;
import com.wosai.bright.mapper.SysUserMapper;
import com.wosai.bright.model.SysUser;
import com.wosai.bright.service.SysUserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("sysUserService")
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysUser> findUser(Map<String, Object> condition) {
        return sysUserMapper.findUser(condition);
    }

    @Override
    public SysUser getUser(Short userId) {
        return sysUserMapper.getUser(userId);
    }

    @Override
    public int checkPassword(Integer userId, String password) {
        return sysUserMapper.checkPassword(userId,password);
    }

    @Override
    public Object saveProfile(HttpServletRequest request,Integer id,MultipartFile file) {


        // 上传文件
        try {
            String imgUrl = FileUtil_ws.uploadFileCheckImgAndLocal(request, file, "image/appProfile");

            SysUser sysUser = sysUserMapper.getUser(id.shortValue()); //获取
            sysUser.setPhoto(imgUrl);       //头像路径

            sysUserMapper.updateByPrimaryKey(sysUser); //保存

        } catch (final Exception e) {
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", e.getMessage());
            }};
        }


        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }
}
