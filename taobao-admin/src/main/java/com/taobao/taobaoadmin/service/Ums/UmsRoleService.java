package com.taobao.taobaoadmin.service.Ums;

import com.taobao.taobaoadmin.model.UmsPermission;
import com.taobao.taobaoadmin.model.UmsRole;

import java.util.List;

public interface UmsRoleService {


    List<UmsRole> list();


    int create(UmsRole role);

    List<UmsPermission> getPermissionList(Long roleId);

    int updatePermission(Long roleId, List<Long> permissionIds);

    int update(Long id, UmsRole role);

    int delete(List<Long> ids);
}
