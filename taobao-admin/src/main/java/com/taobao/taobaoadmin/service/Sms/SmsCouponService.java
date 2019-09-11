package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsCouponParam;
import com.taobao.taobaoadmin.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券管理Service
 */
public interface SmsCouponService {

    /**
     * 分页获取优惠券列表
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);




    /**
     * 根据优惠券id更新优惠券信息
     */
    @Transactional
    int update(Long id, SmsCouponParam couponParam);

    /**
     * 获取优惠券详情
     * @param id 优惠券表id
     */
    SmsCouponParam getItem(Long id);

    /**
     * 添加优惠券
     */
    @Transactional
    int create(SmsCouponParam couponParam);

    int delete(Long id);
}
