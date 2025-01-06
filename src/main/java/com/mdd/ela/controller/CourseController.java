package com.mdd.ela.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 9:51 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/course")
public class CourseController {
    @Autowired
    CourseService service;

    @GetMapping("/find-all")
    public ResponseEntity<Object> findAll(){
        var response = service.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> select(@PathVariable long id){
        var response = service.select(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/insert",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insert(@RequestPart("courseInsertForm") String courseInsertForm,  @RequestParam(value = "image") MultipartFile image){
        CourseInsertForm form = null;
        BaseResponse baseResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            form = objectMapper.readValue(courseInsertForm, CourseInsertForm.class);
            baseResponse = service.insert(form, image);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/update",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestPart("courseUpdateForm") String courseUpdateForm,  @RequestParam(value = "image") MultipartFile image){
        CourseUpdateForm form = null;
        BaseResponse baseResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            form = objectMapper.readValue(courseUpdateForm, CourseUpdateForm.class);
            baseResponse = service.update(form, image);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam long id){
        var response = service.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
