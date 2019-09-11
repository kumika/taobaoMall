package com.taobao.taobaoadmin.service.Pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryWithChildrenItem;
import com.taobao.taobaoadmin.model.PmsProductCategory;

import java.util.List;

public interface PmsProductCategoryService {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();

    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    PmsProductCategory getItem(Long id);

    int update(Long id, PmsProductCategoryParam productCategoryParam);

    int delete(Long id);

    int create(PmsProductCategoryParam productCategoryParam);
}
