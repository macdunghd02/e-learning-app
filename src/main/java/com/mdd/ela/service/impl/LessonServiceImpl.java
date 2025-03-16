package com.mdd.ela.service.impl;

import com.mdd.ela.dto.lesson.LessonResponse;
import com.mdd.ela.dto.lessonvideo.LessonVideoResponse;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.LessonRepository;
import com.mdd.ela.repository.LessonVideoRepository;
import com.mdd.ela.service.LessonService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    LessonVideoRepository lessonVideoRepository;

    public LessonServiceImpl(LessonRepository repository, LessonVideoRepository lessonVideoRepository) {
        this.repository = repository;
        this.lessonVideoRepository = lessonVideoRepository;
    }

    @Override
    public APIResponse getDetail(long id) {
        LessonVideoResponse lessonVideoResponse = lessonVideoRepository.getDetailByLessonId(id);
        LessonResponse response = repository.getDetail(id);
        response.setLessonVideoResponse(lessonVideoResponse);
        return APIResponse.success(response);
    }

    @Override
    public APIResponse create(LessonRequest request) {
        long userId = LoggedInUserUtil.getIdOfLoggedInUser();
        request.setCreateUserId(userId);
        repository.insert(request);
        request.getLessonVideoRequest().setLessonId(request.getId());
        lessonVideoRepository.insert(request.getLessonVideoRequest());
        LessonResponse response = repository.getDetail(request.getId());
        response.setLessonVideoResponse(lessonVideoRepository.getDetailByLessonId(request.getId()));
        return APIResponse.success(response);
    }

    @Override
    public APIResponse update(LessonRequest request) {
        long userId = LoggedInUserUtil.getIdOfLoggedInUser();
        request.setModifyUserId(userId);
        repository.update(request);
        request.getLessonVideoRequest().setLessonId(request.getId());
        lessonVideoRepository.update(request.getLessonVideoRequest());
        LessonResponse response = repository.getDetail(request.getId());
        response.setLessonVideoResponse(lessonVideoRepository.getDetailByLessonId(request.getId()));
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse delete(long id) {
        lessonVideoRepository.deleteByLessonId(id);
        return APIResponse.success(repository.delete(id));
    }
}
