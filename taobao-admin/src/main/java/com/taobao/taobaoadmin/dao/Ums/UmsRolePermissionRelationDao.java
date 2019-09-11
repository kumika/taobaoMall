package com.taobao.taobaoadmin.dao.Ums;

import com.taobao.taobaoadmin.model.UmsPermission;
import com.taobao.taobaoadmin.model.UmsRolePermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户角色管理自定义Dao
 */
public interface UmsRolePermissionRelationDao {
    /**
     * 根据角色获取权限
     */
    List<UmsPermission> getPermissionList(@Param("roleId") Long roleId);

    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list") List<UmsRolePermissionRelation> relationList);
}
