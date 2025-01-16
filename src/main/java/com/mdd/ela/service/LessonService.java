package com.mdd.ela.service;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.dto.lesson.LessonUpdateRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.model.base.BaseResponse;
import com.mdd.ela.model.base.DataResponse;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

public interface LessonService {
    APIResponse getDetail(long id);
    APIResponse create(LessonRequest request);
    APIResponse update(LessonUpdateRequest request);
    APIResponse delete(long id);
}
