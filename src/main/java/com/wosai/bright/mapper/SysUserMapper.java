package com.wosai.bright.mapper;

import com.wosai.bright.model.SysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser>, MySqlMapper<SysUser> {

    List<SysUser> findUser(Map<String, Object> condition);

    SysUser getUser(Short userId);

    /**
     * 验证原密码（app）
     * @param userId
     * @param password
     * @return
     */
    int checkPassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 修改密码（app）
     * @param userId
     * @param password
     * @return
     */
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);


}