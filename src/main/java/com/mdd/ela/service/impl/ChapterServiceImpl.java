package com.mdd.ela.service.impl;

import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.repository.ChapterRepository;
import com.mdd.ela.service.ChapterService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dungmd
 * @created 1/16/2025 3:21 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterServiceImpl implements ChapterService {
    ChapterRepository repository;

    public ChapterServiceImpl(ChapterRepository repository) {
        this.repository = repository;
    }

    @Override
    public APIResponse getAll(long courseId) {
        return APIResponse.success(repository.getAll(courseId));
    }

    @Override
    public APIResponse create(ChapterRequest request) {
        repository.create(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse update(ChapterUpdateRequest request) {
        repository.update(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse delete(long id) {
        return APIResponse.success(repository.delete(id));

    }
}
