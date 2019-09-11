package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsHomeBrand;

import java.util.List;

public interface SmsHomeBrandService {

    int create(List<SmsHomeBrand> homeBrandList);


    /**
     * 分页查询品牌推荐
     */
    List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    /**
     * 修改品牌排序
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 批量修改推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int delete(List<Long> ids);
}
