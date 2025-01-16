package com.mdd.ela.service;

import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import com.mdd.ela.dto.course.CourseRequest;
import com.mdd.ela.model.base.APIResponse;

import java.util.Map;

/**
 * @author dungmd
 * @created 1/16/2025 3:20 下午
 * @project e-learning-app
 */

public interface ChapterService {
    APIResponse getAll(long courseId);
    APIResponse create(ChapterRequest request);
    APIResponse update(ChapterUpdateRequest request);
    APIResponse delete(long id);
}
