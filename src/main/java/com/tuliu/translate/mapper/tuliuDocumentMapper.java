package com.tuliu.translate.mapper;

import com.tuliu.translate.entity.tuliuDocument;
import com.tuliu.translate.entity.tuliuDocumentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface tuliuDocumentMapper {
    int countByExample(tuliuDocumentExample example);

    int deleteByExample(tuliuDocumentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(tuliuDocument record);

    int insertSelective(tuliuDocument record);

    List<tuliuDocument> selectByExample(tuliuDocumentExample example);

    tuliuDocument selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") tuliuDocument record, @Param("example") tuliuDocumentExample example);

    int updateByExample(@Param("record") tuliuDocument record, @Param("example") tuliuDocumentExample example);

    int updateByPrimaryKeySelective(tuliuDocument record);

    int updateByPrimaryKey(tuliuDocument record);
}
