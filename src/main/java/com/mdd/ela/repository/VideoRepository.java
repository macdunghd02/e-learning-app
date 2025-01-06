package com.mdd.ela.repository;

import com.mdd.ela.dto.model.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@Mapper
public interface VideoRepository {
    List<Video> findAll(long courseId);
    Video select(long id);
    int insert(Video form);
    int update(Video form);
    int delete(long id);
}
