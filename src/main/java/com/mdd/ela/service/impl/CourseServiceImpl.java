package com.mdd.ela.service.impl;

import com.cloudinary.Cloudinary;
import com.mdd.ela.dto.model.Course;
import com.mdd.ela.dto.request.course.CourseNoteRequest;
import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.request.course.CourseResponse;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.CourseNoteRepository;
import com.mdd.ela.repository.CourseRepository;
import com.mdd.ela.service.CourseNoteService;
import com.mdd.ela.service.CourseService;
import com.mdd.ela.util.ErrorCode;
import com.mdd.ela.util.LoggedInUserUtil;
import com.mdd.ela.util.PagingUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author dungmd
 * @created 1/4/2025 10:15 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {

    CourseRepository repository;
    CourseNoteService courseNoteService;
    CourseNoteRepository courseNoteRepository;

    public CourseServiceImpl(CourseRepository repository, CourseNoteService courseNoteService, CourseNoteRepository courseNoteRepository) {
        this.repository = repository;
        this.courseNoteService = courseNoteService;
        this.courseNoteRepository = courseNoteRepository;
    }


    @Override
    public APIResponse getAll(Map<String,Object> reqMap) {
        int limit = PagingUtil.getLimit((Integer) reqMap.get("pageSize"));
        int offset = PagingUtil.getOffset((Integer) reqMap.get("pageSize"), (Integer) reqMap.get("pageNum"));
        reqMap.put("limit", limit);
        reqMap.put("offset", offset);
        List<CourseResponse> courseResponseList = repository.getAll(reqMap);
        int count = repository.getCount(reqMap);
        for(CourseResponse courseResponse : courseResponseList){
            courseResponse.setCourseNoteResponseList(courseNoteRepository.getAllByCourseId(courseResponse.getId()));
        }
        return APIResponse.success(courseResponseList,Map.of(
                "timestamp", LocalDateTime.now(),
                "pageSize", reqMap.get("pageSize"),
                "pageNum", reqMap.get("pageNum"),
                "totalRecords", count
                ));
    }

    @Override
    public APIResponse getDetail(long id) {
        CourseResponse course = repository.getDetail(id);
        course.setCourseNoteResponseList(courseNoteRepository.getAllByCourseId(id));
        return APIResponse.success(course);
    }

    @Override
    public APIResponse create(CourseRequest request) {
        request.setAuthorAccountId(LoggedInUserUtil.getIdOfLoggedInUser());
        repository.create(request);
        request.getCourseNoteRequest().setCourseId(request.getId());
        courseNoteService.saveCourseNotes(request.getCourseNoteRequest());
        CourseResponse courseResponse = repository.getDetail(request.getId());
        courseResponse.setCourseNoteResponseList(courseNoteRepository.getAllByCourseId(request.getId()));
        return APIResponse.success(courseResponse);
    }

    @Override
    public APIResponse update(CourseRequest request) {
        request.setModifyUserId(LoggedInUserUtil.getIdOfLoggedInUser());
        repository.update(request);
        request.getCourseNoteRequest().setCourseId(request.getId());
        courseNoteService.saveCourseNotes(request.getCourseNoteRequest());
        CourseResponse courseResponse = repository.getDetail(request.getId());
        courseResponse.setCourseNoteResponseList(courseNoteRepository.getAllByCourseId(request.getId()));
        return APIResponse.success(courseResponse);
    }

    @Override
    public APIResponse delete(long id) {
        repository.delete(id);
        courseNoteRepository.deleteByCourseId(id);
        return APIResponse.success(null);
    }


}
