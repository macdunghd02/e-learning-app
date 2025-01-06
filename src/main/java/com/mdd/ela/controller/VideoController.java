package com.mdd.ela.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.ela.dto.model.Video;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/video")
public class VideoController {
    @Autowired
    VideoService service;

    @GetMapping("/find-all")
    public ResponseEntity<Object> findAll(@RequestParam long courseId){
        var response = service.findAll(courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> select(@PathVariable long id){
        var response = service.select(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/insert",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insert(@RequestPart("videoForm") String videoForm,  @RequestParam(value = "file") MultipartFile file){
        Video form = null;
        BaseResponse baseResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            form = objectMapper.readValue(videoForm, Video.class);
            baseResponse = service.insert(form, file);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/update",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestPart("video") String videoForm,  @RequestParam(value = "file") MultipartFile file){
        Video form = null;
        BaseResponse baseResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            form = objectMapper.readValue(videoForm, Video.class);
            baseResponse = service.update(form, file);
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

