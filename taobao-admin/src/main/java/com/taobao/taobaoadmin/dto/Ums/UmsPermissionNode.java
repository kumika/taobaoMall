package com.taobao.taobaoadmin.dto.Ums;

import com.taobao.taobaoadmin.model.UmsPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UmsPermissionNode extends UmsPermission {
    @Getter
    @Setter
    private List<UmsPermissionNode> children;
}
