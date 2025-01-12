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
import com.mdd.ela.dto.request.account.SendOtpRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.APIResponse;

import com.mdd.ela.service.AccountService;
import com.mdd.ela.service.BaseRedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    Cloudinary cloudinary;
    @Autowired
    JavaMailSender mailSender;
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

    @Operation(summary = "Update account", description = "accountRequest: {\"firstName\" : \"string\",\n" +
            "    \"lastName\" : \"string\",\n" +
            "    \"dob\" : \"2000-01-01\",\n" +
            "    \"phoneNum\" : \"0123456789\",\n" +
            "    \"address\" : \"string\",\n" +
            "    \"description\" : \"string\"}")
    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> update(@RequestPart @Valid String accountRequest, @PathVariable long id, MultipartFile file) throws JsonProcessingException {

        String avatarUrl = saveAvatarUrl(file);
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        AccountRequest request = objectMapper.readValue(accountRequest, AccountRequest.class);
        request.setAvatarUrl(avatarUrl);
        request.setId(id);
        APIResponse response = service.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String saveAvatarUrl(MultipartFile image) {
        try {
            if (image != null) {
                Map r = this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                String avatarUrl = (String) r.get("secure_url");
                return avatarUrl;
            } else return null;
        } catch (IOException e) {
            throw new RuntimeException("Lưu ảnh thất bại");
        }
    }

    @Operation(summary = "Send OTP to email")
    @PostMapping(value = "/send-otp")
    public ResponseEntity<APIResponse> sendOtp(@RequestBody @Valid SendOtpRequest request) {
        String email = request.getEmail();
        String otp = generateOTP();  // Tạo OTP ngẫu nhiên
        redisService.set(email, otp);
        sendOtpEmail(email, otp);  // Gửi OTP qua email
        return new ResponseEntity<>(APIResponse.success(null, null), HttpStatus.OK);
    }

    private String generateOTP() {
        int otp = (int) (Math.random() * 1000000);
        return String.format("%06d", otp);  // Tạo OTP 6 chữ số
    }

    private void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ela.contact.vn@gmail.com");
            helper.setTo(email);
            helper.setSubject("Xác minh tài khoản email của bạn");
            String htmlContent = "<html><body>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 18px;'>"
                    + "OTP của bạn là: <b>" + otp + "</b>.</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Ela sẽ không bao giờ gửi email cho bạn và yêu cầu bạn tiết lộ hoặc xác minh mật khẩu, thẻ tín dụng hoặc số tài khoản ngân hàng của bạn.</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Nếu bạn nhận được một email đáng ngờ có liên kết cập nhật thông tin tài khoản của mình, đừng nhấp vào liên kết."
                    + "</p>"
                    + "<p style='font-family: Arial, sans-serif; font-size: 14px;'>"
                    + "Thay vào đó, hãy báo cáo email đó cho Ela để điều tra.</p>"
                    + "</body></html>";
            helper.setText(htmlContent,true);

            mailSender.send(message); // Gửi email
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi OTP tới email của bạn");
        }
    }
}


