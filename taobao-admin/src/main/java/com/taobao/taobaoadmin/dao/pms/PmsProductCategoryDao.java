package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * 商品分类自定义Dao
 */
public interface PmsProductCategoryDao {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
