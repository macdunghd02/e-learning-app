package com.mdd.ela.service.impl;

import com.mdd.ela.dto.lesson.LessonUpdateRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.model.entity.Lesson;
import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.base.BaseResponse;
import com.mdd.ela.model.base.DataResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.LessonRepository;
import com.mdd.ela.service.LessonService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonServiceImpl implements LessonService {
    LessonRepository repository;

    public LessonServiceImpl(LessonRepository repository) {
        this.repository = repository;
    }

    @Override
    public APIResponse getDetail(long id) {
        return APIResponse.success(repository.getDetail(id));
    }

    @Override
    public APIResponse create(LessonRequest request) {
        long userId = LoggedInUserUtil.getIdOfLoggedInUser();
        request.setCreateUserId(userId);
        repository.insert(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse update(LessonUpdateRequest request) {
        long userId = LoggedInUserUtil.getIdOfLoggedInUser();
        request.setModifyUserId(userId);
        repository.update(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse delete(long id) {
        return APIResponse.success(repository.delete(id));
    }
}
