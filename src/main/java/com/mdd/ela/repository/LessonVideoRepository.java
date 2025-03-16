package com.mdd.ela.repository;

import com.mdd.ela.dto.lessonvideo.LessonVideoRequest;
import com.mdd.ela.dto.lessonvideo.LessonVideoResponse;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dungmd
 * @created 3/13/2025 5:46 下午
 * @project e-learning-app
 */

@Mapper
public interface LessonVideoRepository {
    LessonVideoResponse getDetailByLessonId(long id);
    int insert(LessonVideoRequest request);
    int update(LessonVideoRequest request);
    int deleteByLessonId(long id);
}
