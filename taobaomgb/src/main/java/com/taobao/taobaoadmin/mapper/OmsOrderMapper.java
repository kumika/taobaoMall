package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.OmsOrder;
import com.taobao.taobaoadmin.model.OmsOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsOrderMapper {
    int countByExample(OmsOrderExample example);

    int deleteByExample(OmsOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    List<OmsOrder> selectByExample(OmsOrderExample example);

    OmsOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByExample(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);
}