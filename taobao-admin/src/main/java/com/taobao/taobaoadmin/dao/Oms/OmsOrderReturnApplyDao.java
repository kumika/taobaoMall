package com.taobao.taobaoadmin.dao.Oms;

import com.taobao.taobaoadmin.dto.Oms.OmsOrderReturnApplyResult;
import com.taobao.taobaoadmin.dto.Oms.OmsReturnApplyQueryParam;
import com.taobao.taobaoadmin.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 订单退货申请自定义Dao
 */
public interface OmsOrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);


    /**
     * 获取申请详情
     */
    OmsOrderReturnApplyResult getDetail(@Param("id") Long id);
}
