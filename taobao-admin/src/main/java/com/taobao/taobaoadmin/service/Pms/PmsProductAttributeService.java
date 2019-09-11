package com.taobao.taobaoadmin.service.Pms;

import com.taobao.taobaoadmin.dto.Pms.ProductAttrInfo;
import com.taobao.taobaoadmin.model.PmsProductAttribute;

import java.util.List;

public interface PmsProductAttributeService {
    List<PmsProductAttribute> getList(Long cid, Integer type,Integer pageSize, Integer pageNum);

    /**
     * 根据商品分类的id获取商品属性及属性分类
     * @param productCategoryId
     * @return
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
