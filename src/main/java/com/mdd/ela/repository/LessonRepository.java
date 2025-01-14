package com.mdd.ela.repository;

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
    List<Lesson> findAll(long courseId);
    Lesson select(long id);
    int insert(Lesson form);
    int update(Lesson form);
    int delete(long id);
}
