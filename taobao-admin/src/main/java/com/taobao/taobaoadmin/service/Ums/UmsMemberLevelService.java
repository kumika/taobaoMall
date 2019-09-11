package com.taobao.taobaoadmin.service.Ums;

import com.taobao.taobaoadmin.model.UmsMemberLevel;

import java.util.List;

public interface UmsMemberLevelService {
    List<UmsMemberLevel> list(Integer defaultStatus);
}
