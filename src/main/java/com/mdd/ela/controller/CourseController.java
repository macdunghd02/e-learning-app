package com.mdd.ela.controller;

import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/course")
@Tag(name = "Course Controller")
public class CourseController {

    @Autowired
    private CourseService service;

    @Operation(summary = "Create course")
    @PostMapping
    public ResponseEntity<APIResponse> create(@RequestBody CourseRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @Operation(summary = "Update course")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> update(@PathVariable long id, @RequestBody CourseRequest request) {
        request.setId(id);
        return new ResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @Operation(summary = "Delete course")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Get course detail")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCourseDetails(@PathVariable long id) {
        return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all course")
    @GetMapping
    public ResponseEntity<APIResponse> getAllCourses() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
