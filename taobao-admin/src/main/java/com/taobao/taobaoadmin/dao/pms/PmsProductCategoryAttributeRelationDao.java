package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 自定义商品分类和属性关系Dao
 */
public interface PmsProductCategoryAttributeRelationDao {
    void insertList(@Param("list") List<PmsProductCategoryAttributeRelation> relationList);
}
