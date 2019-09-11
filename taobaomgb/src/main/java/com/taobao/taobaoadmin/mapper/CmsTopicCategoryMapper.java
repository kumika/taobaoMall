package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.CmsTopicCategory;
import com.taobao.taobaoadmin.model.CmsTopicCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsTopicCategoryMapper {
    int countByExample(CmsTopicCategoryExample example);

    int deleteByExample(CmsTopicCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsTopicCategory record);

    int insertSelective(CmsTopicCategory record);

    List<CmsTopicCategory> selectByExample(CmsTopicCategoryExample example);

    CmsTopicCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsTopicCategory record, @Param("example") CmsTopicCategoryExample example);

    int updateByExample(@Param("record") CmsTopicCategory record, @Param("example") CmsTopicCategoryExample example);

    int updateByPrimaryKeySelective(CmsTopicCategory record);

    int updateByPrimaryKey(CmsTopicCategory record);
}