package com.taobao.taobaoadmin.dao.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

/**
 * 优惠券管理自定义查询Dao
 */
public interface SmsCouponDao {
    SmsCouponParam getItem(@Param("id") Long id);
}
