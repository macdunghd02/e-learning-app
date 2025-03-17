package com.mdd.ela.repository;

import com.mdd.ela.dto.base.ComboBoxResponse;
import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterResponse;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/16/2025 3:20 下午
 * @project e-learning-app
 */
@Mapper
public interface ChapterRepository {
    ChapterResponse getDetail(long id);
    List<ChapterResponse> getAll(long courseId);
    int create(ChapterRequest request);
    int update(ChapterUpdateRequest request);
    int delete(long id);
    List<ChapterResponse> getAllComboBox(Map<String,Object> reqMap);
    int getCount(Map<String,Object> reqMap);
    List<ComboBoxResponse> getAllForComboBox(long courseId);
}
