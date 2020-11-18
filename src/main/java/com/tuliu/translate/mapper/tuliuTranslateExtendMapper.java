package com.tuliu.translate.mapper;

import com.tuliu.translate.entity.tuliuTranslate;
import com.tuliu.translate.entity.tuliuTranslateExample;
import com.tuliu.translate.entity.tuliuTranslateExtendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface tuliuTranslateExtendMapper {

  List<String> getDocument();

  List<tuliuTranslate> selectByMultiple(
      @Param("name") String name,
      @Param("words") String words,
      @Param("cols") List<String> cols,
      @Param("offset") int offset,
      @Param("limit") int limit
  );

  long countByExample(@Param("name") String name, tuliuTranslateExample example);

  long countByMultiple(@Param("name") String name, @Param("words") String words);

  void appendTableColumn(@Param("col") String col);

  List<String> getColumn();

  List<tuliuTranslate> selectByExample(tuliuTranslateExtendExample example);
}
