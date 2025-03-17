package com.mdd.ela.service;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.base.APIResponse;

import java.util.Map;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

public interface LessonService {
    APIResponse getDetail(long id);
    APIResponse create(LessonRequest request);
    APIResponse update(LessonRequest request);
    APIResponse delete(long id);
    APIResponse getAll(Map<String,Object> reqMap);
    APIResponse getAll(long chapterId);
}
