package com.taobao.taobaoadmin.dao.Oms;

import com.taobao.taobaoadmin.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 订单操作记录自定义Dao
 */
public interface OmsOrderOperateHistoryDao {

    int insertList(@Param("list") List<OmsOrderOperateHistory> orderOperateHistoryList);
}
