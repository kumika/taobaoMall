package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsHomeAdvertise;

import java.util.List;

/**
 * 首页广告管理Service
 */
public interface SmsHomeAdvertiseService {
    List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取广告详情
     */
    SmsHomeAdvertise getItem(Long id);

    /**
     * 修改上、下线状态
     */
    int updateStatus(Long id, Integer status);

    int update(Long id, SmsHomeAdvertise advertise);

    int delete(List<Long> ids);

    int create(SmsHomeAdvertise homeAdvertise);
}
