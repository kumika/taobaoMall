package com.taobao.taobaoadmin.dto.Oms;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 修改订单费用信息参数
 */
@Getter
@Setter
public class OmsMoneyInfoParam {
    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
}
