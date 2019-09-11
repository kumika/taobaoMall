package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.SmsCouponProductRelation;
import com.taobao.taobaoadmin.model.SmsCouponProductRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsCouponProductRelationMapper {
    int countByExample(SmsCouponProductRelationExample example);

    int deleteByExample(SmsCouponProductRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsCouponProductRelation record);

    int insertSelective(SmsCouponProductRelation record);

    List<SmsCouponProductRelation> selectByExample(SmsCouponProductRelationExample example);

    SmsCouponProductRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsCouponProductRelation record, @Param("example") SmsCouponProductRelationExample example);

    int updateByExample(@Param("record") SmsCouponProductRelation record, @Param("example") SmsCouponProductRelationExample example);

    int updateByPrimaryKeySelective(SmsCouponProductRelation record);

    int updateByPrimaryKey(SmsCouponProductRelation record);
}