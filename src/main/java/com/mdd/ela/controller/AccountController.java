package com.mdd.ela.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author dungmd
 * @created 12/24/2024 3:04 PM
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/account")
public class AccountController {
    @Autowired
    AccountService service;
    @Autowired
    Cloudinary cloudinary;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request){
        BaseResponse baseResponse = service.signUp(request);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable long id){
        BaseResponse baseResponse = service.getDetail(id);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable long id){
        BaseResponse baseResponse = service.changePassword(request,id);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestPart @Valid String accountRequest, @PathVariable long id, MultipartFile file) throws JsonProcessingException {

        String avatarUrl = saveAvatarUrl(file);
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        AccountRequest request = objectMapper.readValue(accountRequest, AccountRequest.class);
        request.setAvatarUrl(avatarUrl);
        BaseResponse baseResponse = service.update(request,id);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    private String saveAvatarUrl(MultipartFile image) {
        try {
            Map r = this.cloudinary.uploader().upload(image.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String avatarUrl = (String) r.get("secure_url");
            return avatarUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving image");
        }
    }

}
