package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.CmsTopicComment;
import com.taobao.taobaoadmin.model.CmsTopicCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsTopicCommentMapper {
    int countByExample(CmsTopicCommentExample example);

    int deleteByExample(CmsTopicCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsTopicComment record);

    int insertSelective(CmsTopicComment record);

    List<CmsTopicComment> selectByExample(CmsTopicCommentExample example);

    CmsTopicComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsTopicComment record, @Param("example") CmsTopicCommentExample example);

    int updateByExample(@Param("record") CmsTopicComment record, @Param("example") CmsTopicCommentExample example);

    int updateByPrimaryKeySelective(CmsTopicComment record);

    int updateByPrimaryKey(CmsTopicComment record);
}