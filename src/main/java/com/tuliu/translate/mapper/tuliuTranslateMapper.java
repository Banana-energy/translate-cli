package com.tuliu.translate.mapper;

import com.tuliu.translate.entity.tuliuTranslate;
import com.tuliu.translate.entity.tuliuTranslateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface tuliuTranslateMapper {
    int countByExample(tuliuTranslateExample example);

    int deleteByExample(tuliuTranslateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(tuliuTranslate record);

    int insertSelective(tuliuTranslate record);

    List<tuliuTranslate> selectByExample(tuliuTranslateExample example);

    tuliuTranslate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") tuliuTranslate record, @Param("example") tuliuTranslateExample example);

    int updateByExample(@Param("record") tuliuTranslate record, @Param("example") tuliuTranslateExample example);

    int updateByPrimaryKeySelective(tuliuTranslate record);

    int updateByPrimaryKey(tuliuTranslate record);
}