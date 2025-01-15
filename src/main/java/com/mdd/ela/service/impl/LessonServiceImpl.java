package com.mdd.ela.service.impl;

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
    public DataResponse getAll(long courseId) {
        try {
            List<Lesson> lessonList = repository.findAll(courseId);
            return DataResponse.builder().data(lessonList).build();
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse getDetail(long id) {
        try {
            Lesson lesson = repository.select(id);
            return DataResponse.builder().data(lesson).build();
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse create(LessonRequest request) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            request.setCreateUserId(userId);
            int res = repository.insert(request);
            if(res != 1)
                throw new AppRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse update(long id,LessonRequest request) {
        return null;
    }

    @Override
    public BaseResponse delete(long id) {
        return null;
    }
}
