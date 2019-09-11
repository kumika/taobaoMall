package com.taobao.taobaoadmin.service.Pms;

import com.taobao.taobaoadmin.dto.Pms.PmsBrandParam;

import java.util.List;

/**
 * 商品品牌Service
 */
public interface PmsBrandService {

    List listBrand(String keyword, Integer pageNum, Integer pageSize);

    Object getBrand(Long id);

    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    int createBrand(PmsBrandParam pmsBrand);

    int deleteBrand(Long id);

    int deleteBrandBatch(List<Long> ids);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);
}
