package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义商品满减Dao
 */
public interface PmsProductFullReductionDao {
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
