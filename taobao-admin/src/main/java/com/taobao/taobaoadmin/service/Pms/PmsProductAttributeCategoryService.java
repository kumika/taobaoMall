package com.taobao.taobaoadmin.service.Pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductAttributeCategoryItem;
import com.taobao.taobaoadmin.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 商品属性分类Service
 */
public interface PmsProductAttributeCategoryService {
    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    List<PmsProductAttributeCategoryItem> getListWithAttr();

    int update(Long id, String name);

    int create(String name);

    int delete(Long id);
}
