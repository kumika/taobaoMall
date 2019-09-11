package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * 自定义商品属性分类Dao
 */
public interface PmsProductAttributeCategoryDao {
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
