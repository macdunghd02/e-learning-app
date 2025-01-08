package com.mdd.ela.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mdd.ela.dto.model.Lesson;
import com.mdd.ela.dto.request.lesson.LessonRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.repository.LessonRepository;
import com.mdd.ela.service.LessonService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

@Service
@Transactional(rollbackFor = ElaRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonServiceImpl implements LessonService {
    LessonRepository repository;
    Cloudinary cloudinary;

    public LessonServiceImpl(LessonRepository repository, Cloudinary cloudinary) {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    @Override
    public DataResponse getAll(long courseId) {
        try {
            List<Lesson> lessonList = repository.findAll(courseId);
            return DataResponse.builder().data(lessonList).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse getDetail(long id) {
        try {
            Lesson lesson = repository.select(id);
            return DataResponse.builder().data(lesson).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse create(LessonRequest request) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            request.setCreateUserId(userId);
            int res = repository.insert(request);
            if(res != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
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
