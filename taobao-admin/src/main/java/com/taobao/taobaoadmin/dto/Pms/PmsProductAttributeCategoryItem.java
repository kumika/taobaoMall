package com.taobao.taobaoadmin.dto.Pms;

import com.taobao.taobaoadmin.model.PmsProductAttribute;
import com.taobao.taobaoadmin.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 包含有分类下属性的dto
 */
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory{
    private List<PmsProductAttribute> productAttributeList;

    public List<PmsProductAttribute> getProductAttributeList() {
        return productAttributeList;
    }

    public void setProductAttributeList(List<PmsProductAttribute> productAttributeList) {
        this.productAttributeList = productAttributeList;
    }
}
