package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionSessionDetail;
import com.taobao.taobaoadmin.model.SmsFlashPromotionSession;

import java.util.List;

public interface SmsFlashPromotionSessionService {

    /**
     * 根据启用状态获取场次列表
     */
    List<SmsFlashPromotionSession> list();

    /**
     * 获取全部可选场次及其数量
     */
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);

    /**
     * 添加场次
     */
    int create(SmsFlashPromotionSession promotionSession);

    /**
     * 修改场次启用状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 修改场次
     */
    int update(Long id, SmsFlashPromotionSession promotionSession);

    /**
     * 删除场次
     */
    int delete(Long id);
}
