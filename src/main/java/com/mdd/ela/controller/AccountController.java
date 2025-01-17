package com.mdd.ela.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mdd.ela.dto.account.ChangePasswordRequest;
import com.mdd.ela.dto.account.AccountRequest;
import com.mdd.ela.dto.account.SendOtpRequest;
import com.mdd.ela.dto.account.SignUpRequest;
import com.mdd.ela.model.base.APIResponse;

import com.mdd.ela.service.AccountService;
import com.mdd.ela.service.base.BaseRedisService;
import com.mdd.ela.service.base.BaseMailService;
import com.mdd.ela.service.base.BaseS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 12/24/2024 3:04 PM
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/account")
@Tag(name = "Account Controller")
public class AccountController {

    @Autowired
    AccountService service;
    @Autowired
    BaseS3Service baseS3Service;
    @Autowired
    BaseMailService baseMailService;
    @Autowired
    BaseRedisService redisService;

    @Operation(summary = "Sign up")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request, @RequestParam String otp){
        return new ResponseEntity<>(service.signUp(request), HttpStatus.OK);
    }

    @Operation(summary = "Get account detail")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAccountDetail(@PathVariable long id) {
        return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
    }

    @Operation(summary = "Change password")
    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<APIResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable long id) {
        request.setId(id);
        return new ResponseEntity<>(service.changePassword(request), HttpStatus.OK);
    }

    @Operation(summary = "Update account")
    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse> updateAccount(@RequestPart  @Valid AccountRequest request,
                                              @PathVariable long id,
                                              @RequestPart(required = false) MultipartFile file) {
        String avatarUrl = baseS3Service.saveFile(file,"image");
        request.setAvatarUrl(avatarUrl);
        request.setId(id);
        return new ResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @Operation(summary = "Send OTP")
    @PostMapping(value = "/send-otp")
    public ResponseEntity<APIResponse> sendOtp(@RequestBody @Valid SendOtpRequest request) {
        return new ResponseEntity<>(APIResponse.success(service.sendOtp(request)), HttpStatus.OK);
    }
}


