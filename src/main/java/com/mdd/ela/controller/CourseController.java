package com.mdd.ela.controller;

import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid CourseInsertForm form){
        var response = service.insert(form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody @Valid CourseUpdateForm form){
        var response = service.update(form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam long id){
        var response = service.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
