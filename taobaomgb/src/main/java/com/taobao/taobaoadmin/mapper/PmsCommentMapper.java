package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.PmsComment;
import com.taobao.taobaoadmin.model.PmsCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsCommentMapper {
    int countByExample(PmsCommentExample example);

    int deleteByExample(PmsCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsComment record);

    int insertSelective(PmsComment record);

    List<PmsComment> selectByExampleWithBLOBs(PmsCommentExample example);

    List<PmsComment> selectByExample(PmsCommentExample example);

    PmsComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsComment record, @Param("example") PmsCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") PmsComment record, @Param("example") PmsCommentExample example);

    int updateByExample(@Param("record") PmsComment record, @Param("example") PmsCommentExample example);

    int updateByPrimaryKeySelective(PmsComment record);

    int updateByPrimaryKeyWithBLOBs(PmsComment record);

    int updateByPrimaryKey(PmsComment record);
}