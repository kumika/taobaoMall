package com.taobao.taobaoadmin.dao.pms;

import com.taobao.taobaoadmin.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义会员阶梯价格Dao
 */
public interface PmsProductLadderDao {
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
