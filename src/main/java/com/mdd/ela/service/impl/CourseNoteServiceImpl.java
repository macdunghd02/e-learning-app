package com.mdd.ela.service.impl;

import com.mdd.ela.model.entity.CourseNote;
import com.mdd.ela.dto.course.CourseNoteRequest;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.CourseNoteRepository;
import com.mdd.ela.service.CourseNoteService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dungmd
 * @created 1/12/2025 2:50 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseNoteServiceImpl implements CourseNoteService {

    CourseNoteRepository repository;

    public CourseNoteServiceImpl(CourseNoteRepository repository) {
        this.repository = repository;
    }


    public void saveCourseNotes(CourseNoteRequest request) {
        repository.deleteByCourseId(request.getCourseId());
        // Tạo danh sách bản ghi mới
        List<CourseNote> courseNotes = new ArrayList<>();

        if (request.getTargetGoal() != null) {
            for (String goal : request.getTargetGoal()) {
                courseNotes.add(new CourseNote(null, request.getCourseId(), 1, goal));
            }
        }

        if (request.getRequireSkill() != null) {
            for (String skill : request.getRequireSkill()) {
                courseNotes.add(new CourseNote(null, request.getCourseId(), 2, skill));
            }
        }

        if (request.getSuitableFor() != null) {
            for (String suitable : request.getSuitableFor()) {
                courseNotes.add(new CourseNote(null, request.getCourseId(), 3, suitable));
            }
        }

        if (!courseNotes.isEmpty()) {
            repository.insertCourseNote(courseNotes);
        }
    }
}
