package com.mdd.ela.controller;

import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import com.mdd.ela.dto.course.CourseRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.service.ChapterService;
import com.mdd.ela.service.CourseService;
import com.mdd.ela.service.base.BaseS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/16/2025 3:20 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/chapter")
@Tag(name = "Chapter Controller")
public class ChapterController {
    @Autowired
    private ChapterService service;

    @Operation(summary = "Create chapter")
    @PostMapping()
    public ResponseEntity<APIResponse> create(@RequestBody ChapterRequest request){
        return new ResponseEntity<APIResponse>(service.create(request), HttpStatus.OK);
    }

    @Operation(summary = "Update course")
    @PutMapping(value = "/{id}")
    public ResponseEntity<APIResponse> update(@PathVariable long id,
                                              @RequestBody ChapterUpdateRequest request){
        request.setId(id);
        return new ResponseEntity<APIResponse>(service.update(request), HttpStatus.OK);
    }

    @Operation(summary = "Delete course")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable long id) {
        return new ResponseEntity<APIResponse>(service.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all course")
    @GetMapping
    public ResponseEntity<APIResponse> getAllCourses(long courseId) {
        return new ResponseEntity<>(service.getAll(courseId), HttpStatus.OK);
    }
}

