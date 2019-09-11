package com.taobao.taobaoadmin.service.IMPL.Oms;

import com.taobao.taobaoadmin.mapper.OmsOrderSettingMapper;
import com.taobao.taobaoadmin.model.OmsOrderSetting;
import com.taobao.taobaoadmin.service.Oms.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单设置管理Service实现类
 */
@Service
public class OmsOrderSettingServiceIMPL implements OmsOrderSettingService {

    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;

    @Override
    public OmsOrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
}
