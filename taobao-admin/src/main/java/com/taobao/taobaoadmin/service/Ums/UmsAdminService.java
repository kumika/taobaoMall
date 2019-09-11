package com.taobao.taobaoadmin.service.Ums;

import com.taobao.taobaoadmin.model.UmsAdmin;
import com.taobao.taobaoadmin.model.UmsPermission;
import com.taobao.taobaoadmin.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员Service
 */
public interface UmsAdminService {
    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminService umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 修改指定用户信息
     */
    int update(Long id, UmsAdmin umsAdmin);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 获取用户对于角色
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 修改用户的+-权限
     */
    @Transactional
    int updatePermission(Long adminId, List<Long> permissionIds);


    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
