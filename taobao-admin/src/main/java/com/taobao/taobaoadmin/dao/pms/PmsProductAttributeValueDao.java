package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsProductAttribute;
import com.taobao.taobaoadmin.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品参数，商品自定义规格属性Dao
 */
public interface PmsProductAttributeValueDao {
    int insertList(@Param("list") List<PmsProductAttributeValue> productAttributeValuesList);
}
