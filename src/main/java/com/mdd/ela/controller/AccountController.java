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


    @Operation(summary = "Create account")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request, @RequestParam String otp) throws BadRequestException {

        if (!otp.equals(redisService.get(request.getEmail()).toString())) {
            throw new BadRequestException("Xác thực OTP không thành công");
        }
        APIResponse response = service.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get account detail")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAccountDetail(@PathVariable long id) {
        APIResponse response = service.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Change password")
    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<APIResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable long id) {
        request.setId(id);
        APIResponse response = service.changePassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update account", description = "accountRequest: {\"fullName\" : \"string\",\n" +
            "    \"dob\" : \"2000-01-01\",\n" +
            "    \"phoneNum\" : \"0123456789\",\n" +
            "    \"address\" : \"string\",\n" +
            "    \"description\" : \"string\"}")
    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> update(@RequestPart @Valid String accountRequest, @PathVariable long id, MultipartFile file) throws JsonProcessingException {

        String avatarUrl = baseS3Service.saveFile(file,"image");
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        AccountRequest request = objectMapper.readValue(accountRequest, AccountRequest.class);
        request.setAvatarUrl(avatarUrl);
        request.setId(id);
        APIResponse response = service.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Send OTP to email")
    @PostMapping(value = "/send-otp")
    public ResponseEntity<APIResponse> sendOtp(@RequestBody @Valid SendOtpRequest request) {
        String email = request.getEmail();
        String otp = generateOTP();  // Tạo OTP ngẫu nhiên
        redisService.set(email, otp);
        baseMailService.sendOtpEmail(email, otp);  // Gửi OTP qua email
        return new ResponseEntity<>(APIResponse.success(null), HttpStatus.OK);
    }

    private String generateOTP() {
        int otp = (int) (Math.random() * 1000000);
        return String.format("%06d", otp);  // Tạo OTP 6 chữ số
    }
}


