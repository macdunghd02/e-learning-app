package com.mdd.ela.repository;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.dto.lesson.LessonUpdateRequest;
import com.mdd.ela.model.entity.Lesson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@Mapper
public interface LessonRepository {
    Lesson getDetail(long id);
    int insert(LessonRequest request);
    int update(LessonUpdateRequest request);
    int delete(long id);
}
