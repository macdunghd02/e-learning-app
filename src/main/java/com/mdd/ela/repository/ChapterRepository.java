package com.mdd.ela.repository;

import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterResponse;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
