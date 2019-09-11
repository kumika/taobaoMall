package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/**
 * 商品自定义Dao
 */
public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
