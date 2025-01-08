package com.mdd.ela.controller;

import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Create a new course
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CourseRequest request) {
        var response = courseService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable long id, @RequestBody CourseRequest request) {
        var response = courseService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable long id) {
        var response = courseService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get details of a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getCourseDetails(@PathVariable long id) {
        var response = courseService.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // List all courses
    @GetMapping
    public ResponseEntity<BaseResponse> getAllCourses() {
        var response = courseService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
