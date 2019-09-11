package com.taobao.taobaoadmin.service.Oms;

import com.taobao.taobaoadmin.dto.Oms.OmsOrderReturnApplyResult;
import com.taobao.taobaoadmin.dto.Oms.OmsReturnApplyQueryParam;
import com.taobao.taobaoadmin.dto.Oms.OmsUpdateStatusParam;
import com.taobao.taobaoadmin.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 退货申请管理Service
 */
public interface OmsOrderReturnApplyService {
    /**
     * 分页查询申请
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     *获取指定申请详情
     */
    OmsOrderReturnApplyResult getItem(Long id);

    /**
     * 修改申请状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);

    /**
     * 批量删除申请
     */
    int delete(List<Long> ids);
}
