package com.mdd.ela.repository;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.dto.lesson.LessonResponse;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@Mapper
public interface LessonRepository {
    LessonResponse getDetail(long id);
    int insert(LessonRequest request);
    int update(LessonRequest request);
    int delete(long id);
}
