package com.taobao.taobaoadmin.mapper;

import com.taobao.taobaoadmin.model.PmsCommentReplay;
import com.taobao.taobaoadmin.model.PmsCommentReplayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsCommentReplayMapper {
    int countByExample(PmsCommentReplayExample example);

    int deleteByExample(PmsCommentReplayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsCommentReplay record);

    int insertSelective(PmsCommentReplay record);

    List<PmsCommentReplay> selectByExample(PmsCommentReplayExample example);

    PmsCommentReplay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsCommentReplay record, @Param("example") PmsCommentReplayExample example);

    int updateByExample(@Param("record") PmsCommentReplay record, @Param("example") PmsCommentReplayExample example);

    int updateByPrimaryKeySelective(PmsCommentReplay record);

    int updateByPrimaryKey(PmsCommentReplay record);
}