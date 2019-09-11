package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.dto.Pms.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义商品属性Dao
 */
public interface PmsProductAttributeDao {

    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
