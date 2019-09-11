package com.taobao.taobaoadmin.dao.Oms;

import com.taobao.taobaoadmin.dto.Oms.OmsOrderDeliveryParam;
import com.taobao.taobaoadmin.dto.Oms.OmsOrderDetail;
import com.taobao.taobaoadmin.dto.Oms.OmsOrderQueryParam;
import com.taobao.taobaoadmin.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单自定义查询Dao
 */
public interface OmsOrderDao {

    /**
     * 条件查询订单
     * @param queryParam
     * @return
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(Long id);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);
}
