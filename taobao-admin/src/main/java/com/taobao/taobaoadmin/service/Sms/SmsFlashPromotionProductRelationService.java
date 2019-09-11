package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.dto.Sms.SmsFlashPromotionProduct;
import com.taobao.taobaoadmin.model.SmsFlashPromotionProductRelation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 限时购商品关联管理Service
 */
public interface SmsFlashPromotionProductRelationService {
    /**
     * 根据活动和场次id获取商品关系数量
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @return
     */
    int getCount(Long flashPromotionId, Long flashPromotionSessionId);

    /**
     * 分页查询相关商品及促销信息
     *
     * @param flashPromotionId        限时购id
     * @param flashPromotionSessionId 限时购场次id
     */
    List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);

    /**
     * 修改关联相关信息
     */
    int update(Long id, SmsFlashPromotionProductRelation relation);

    /**
     * 批量添加关联
     */
    @Transactional
    int create(List<SmsFlashPromotionProductRelation> relationList);

    int delete(Long id);
}
