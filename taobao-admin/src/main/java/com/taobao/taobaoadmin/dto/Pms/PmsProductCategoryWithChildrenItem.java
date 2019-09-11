package com.taobao.taobaoadmin.dto.Pms;

import com.taobao.taobaoadmin.model.PmsProductCategory;

import java.util.List;

/**
 *      一级，二级···次级分类的区分是靠该类对象的一个叫父类ID的属性 parentID
 *      children对象，是parentId 不为0 的PmsProductCategory对象，也就是次级分类对象
 */
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory{

    private List<PmsProductCategory> children;

    public List<PmsProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<PmsProductCategory> children) {
        this.children = children;
    }
}
