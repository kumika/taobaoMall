package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.CmsSubjectProductRelation;
import com.taobao.taobaoadmin.model.CmsSubjectProductRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsSubjectProductRelationMapper {
    int countByExample(CmsSubjectProductRelationExample example);

    int deleteByExample(CmsSubjectProductRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsSubjectProductRelation record);

    int insertSelective(CmsSubjectProductRelation record);

    List<CmsSubjectProductRelation> selectByExample(CmsSubjectProductRelationExample example);

    CmsSubjectProductRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsSubjectProductRelation record, @Param("example") CmsSubjectProductRelationExample example);

    int updateByExample(@Param("record") CmsSubjectProductRelation record, @Param("example") CmsSubjectProductRelationExample example);

    int updateByPrimaryKeySelective(CmsSubjectProductRelation record);

    int updateByPrimaryKey(CmsSubjectProductRelation record);
}