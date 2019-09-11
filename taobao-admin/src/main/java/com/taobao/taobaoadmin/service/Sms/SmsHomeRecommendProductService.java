package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsHomeRecommendProduct;

import java.util.List;

public interface SmsHomeRecommendProductService {
    int create(List<SmsHomeRecommendProduct> homeBrandList);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int updateSort(Long id, Integer sort);

    List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
