package com.taobao.taobaoadmin.dto.Sms;

import com.taobao.taobaoadmin.model.SmsCoupon;
import com.taobao.taobaoadmin.model.SmsCouponProductCategoryRelation;
import com.taobao.taobaoadmin.model.SmsCouponProductRelation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 优惠券信息封装，包括绑定商品和绑定分类
 */
@Setter
@Getter
public class SmsCouponParam extends SmsCoupon {
    //优惠券绑定的商品
    private List<SmsCouponProductRelation> productRelationList;

    //优惠券绑定的商品分类
    private List<SmsCouponProductCategoryRelation> productCategoryRelationList;
}
