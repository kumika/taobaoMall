package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.mapper.SmsCouponHistoryMapper;
import com.taobao.taobaoadmin.model.SmsCouponHistory;
import com.taobao.taobaoadmin.model.SmsCouponHistoryExample;
import com.taobao.taobaoadmin.service.Sms.SmsCouponHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class SmsCouponHistoryServiceIMPL implements SmsCouponHistoryService {

    @Autowired
    private SmsCouponHistoryMapper historyMapper;


    @Override
    public List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);


        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
        if (couponId != null) {
            criteria.andCouponIdEqualTo(couponId);
        }
        if (useStatus != null) {
            criteria.andUseStatusEqualTo(useStatus);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        return historyMapper.selectByExample(example);
    }
}
