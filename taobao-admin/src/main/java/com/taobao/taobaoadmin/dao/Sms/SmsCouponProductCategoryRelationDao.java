package com.taobao.taobaoadmin.dao.Sms;

import com.taobao.taobaoadmin.model.SmsCouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 优惠券和商品分类关系自定义Dao
 */
public interface SmsCouponProductCategoryRelationDao {
    void insertList(@Param("list") List<SmsCouponProductCategoryRelation> productCategoryRelationList);
}
