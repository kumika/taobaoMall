package com.taobao.taobaoadmin.service.Ums;

import com.taobao.taobaoadmin.dto.Ums.UmsPermissionNode;
import com.taobao.taobaoadmin.model.UmsPermission;

import java.util.List;

public interface UmsPermissionService {
    List<UmsPermission> list();

    int create(UmsPermission permission);

    int update(Long id, UmsPermission permission);

    int delete(List<Long> ids);

    List<UmsPermissionNode> treeList();
}
