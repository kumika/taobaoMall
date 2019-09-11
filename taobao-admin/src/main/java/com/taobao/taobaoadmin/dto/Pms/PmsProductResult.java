package com.taobao.taobaoadmin.dto.Pms;

import com.taobao.taobaoadmin.model.PmsProduct;

/**
 * 查询单个产品进行修改时返回的结果
 */
public class PmsProductResult extends PmsProductParam {

    //商品所选分类的父id
    private Long cateParentId;

    public Long getCateParentId() {
        return cateParentId;
    }

    public void setCateParentId(Long cateParentId) {
        this.cateParentId = cateParentId;
    }
}
