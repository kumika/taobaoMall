package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义商品sku库存Dao
 */
public interface PmsSkuStockDao {
    /**
     * 批量插入操作
     */
    int insertList(@Param("list") List<PmsSkuStock> skuStockList);

    /**
     * 批量插入或替换操作
     */
    int replaceList(@Param("list") List<PmsSkuStock> skuStockList);

}
