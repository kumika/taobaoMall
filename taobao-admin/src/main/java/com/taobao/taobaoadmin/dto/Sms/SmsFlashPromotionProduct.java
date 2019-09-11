package com.taobao.taobaoadmin.dto.Sms;

import com.taobao.taobaoadmin.model.PmsProduct;
import com.taobao.taobaoadmin.model.SmsFlashPromotionProductRelation;
import lombok.Getter;
import lombok.Setter;

/**
 * 限时购及商品信息封装
 */
public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation{
    @Getter
    @Setter
    private PmsProduct product;
}
