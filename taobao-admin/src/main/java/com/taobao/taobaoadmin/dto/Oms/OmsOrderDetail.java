package com.taobao.taobaoadmin.dto.Oms;

import com.taobao.taobaoadmin.model.OmsOrder;
import com.taobao.taobaoadmin.model.OmsOrderItem;
import com.taobao.taobaoadmin.model.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情信息
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;


    @Getter
    @Setter
    private List<OmsOrderOperateHistory> historyList;
}
