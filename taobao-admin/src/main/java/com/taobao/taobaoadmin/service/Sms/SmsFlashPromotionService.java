package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsFlashPromotion;

import java.util.List;

public interface SmsFlashPromotionService {
    /**
     * 分页查询活动
     */
    List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 修改上下线状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 修改指定活动
     */
    int update(Long id, SmsFlashPromotion flashPromotion);

    int create(SmsFlashPromotion flashPromotion);

    int delete(Long id);
}
