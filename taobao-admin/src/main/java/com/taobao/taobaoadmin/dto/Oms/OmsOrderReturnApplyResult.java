package com.taobao.taobaoadmin.dto.Oms;

import com.taobao.taobaoadmin.model.OmsCompanyAddress;
import com.taobao.taobaoadmin.model.OmsOrderReturnApply;
import lombok.Getter;
import lombok.Setter;

/**
 * 申请信息封装
 */
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @Getter
    @Setter
    private OmsCompanyAddress companyAddress;
}
