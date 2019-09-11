package com.taobao.taobaoadmin.dao.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionProduct;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 限时购商品关联自定义Dao
 */
public interface SmsFlashPromotionProductRelationDao {
    /**
     * 获取限时购及相关商品信息
     */
    List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId") Long flashPromotionId,
                                           @Param("flashPromotionSessionId") Long flashPromotionSessionId);
}
