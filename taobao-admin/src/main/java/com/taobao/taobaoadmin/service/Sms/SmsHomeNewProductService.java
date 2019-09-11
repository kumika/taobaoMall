package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsHomeNewProduct;

import java.util.List;

public interface SmsHomeNewProductService {
    List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    int create(List<SmsHomeNewProduct> homeNewProductList);

    int updateSort(Long id, Integer sort);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int delete(List<Long> ids);
}
