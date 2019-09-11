package com.taobao.taobaoadmin.dao.Sms;

import com.taobao.taobaoadmin.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 优惠券和产品关系自定义Dao
 */
public interface SmsCouponProductRelationDao {
    void insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
}
