package com.taobao.taobaoadmin.service.Oms;

import com.taobao.taobaoadmin.dto.Oms.*;
import com.taobao.taobaoadmin.model.OmsOrder;
import groovy.transform.TailRecursive;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单管理Service
 */
public interface OmsOrderService {
    /**
     * 订单查询
     */
    List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 获取指定订单详情
     */
    OmsOrderDetail detail(Long id);

    /**
     * 批量删除订单
     */
    int delete(List<Long> ids);

    /**
     * 批量发货
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> deliveryParams);

    /**
     * 修改订单收货人信息
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    /**
     * 修改订单备注
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);

    /**
     * 修改订单费用信息
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

    /**
     * 批量关闭订单
     */
    @Transactional
    int close(List<Long> ids, String note);
}
