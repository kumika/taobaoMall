package com.taobao.taobaoadmin.dto.Oms;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单发货参数
 */
@Getter
@Setter
public class OmsOrderDeliveryParam {
    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("物流公司")
    private String deliveryCompany;

    @ApiModelProperty("物流单号")
    private String deliverySn;

}
