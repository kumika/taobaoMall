package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品审核日志自定义dao
 */
public interface PmsProductVertifyRecordDao {
    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
