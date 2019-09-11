package com.taobao.taobaoadmin.service.Oms;

import com.taobao.taobaoadmin.model.OmsOrderReturnReason;

import java.util.List;

/**
 * 订单原因管理Service
 */
public interface OmsOrderReturnReasonService {

    /**
     * 分页获取退货原因
     */
    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

    /**
     * 获取单个退货原因详情信息
     */
    OmsOrderReturnReason getItem(Long id);

    /**
     * 修改退货原因
     */
    int update(Long id, OmsOrderReturnReason returnReason);

    /**
     * 批量修改退货原因状态
     */
    int updateStatus(List<Long> ids, Integer status);

    /**
     * 添加订单原因
     */
    int create(OmsOrderReturnReason returnReason);

    int delete(List<Long> ids);
}
