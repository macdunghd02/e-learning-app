package com.mdd.ela.service.impl;

import com.mdd.ela.dto.account.*;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AccountService;
import com.mdd.ela.service.base.BaseMailService;
import com.mdd.ela.service.base.BaseRedisService;
import com.mdd.ela.util.constants.ErrorCode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dungmd
 * @created 12/19/2024 9:50 AM
 * @project e-learning-app
 */

@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository repository;
    PasswordEncoder encoder;
    BaseRedisService redisService;
    BaseMailService mailService;

    public AccountServiceImpl(AccountRepository repository, PasswordEncoder encoder, BaseRedisService redisService, BaseMailService mailService) {
        this.repository = repository;
        this.encoder = encoder;
        this.redisService = redisService;
        this.mailService = mailService;
    }


    @Override
    public APIResponse signUp(SignUpRequest request) {
        request.setPassword(encoder.encode(request.getPassword()));
        repository.signUp(request);
        AccountResponse accountResponse = repository.getDetail(request.getId());
        return APIResponse.success(accountResponse);
    }

    @Override
    public APIResponse getDetail(long id) {
        AccountResponse response = repository.getDetail(id);
        return APIResponse.success(response);
    }

    @Override
    public APIResponse changePassword(ChangePasswordRequest request) {
        AccountResponse currentAcc = repository.getDetail(request.getId());
        if (!encoder.matches(request.getOldPassword(), currentAcc.getPassword()))
            throw new AppRuntimeException(ErrorCode.WRONG_PASSWORD);
        request.setNewPassword(encoder.encode(request.getNewPassword()));
        repository.changePassword(request);
        return APIResponse.success(null);
    }

    @Override
    public APIResponse update(AccountRequest request) {
        repository.update(request);
        AccountResponse accountResponse = repository.getDetail(request.getId());
        return APIResponse.success(accountResponse);
    }

    @Override
    public APIResponse sendOtp(SendOtpRequest request) {
        if (repository.existsByEmail(request.getEmail()) != 0)
            throw new AppRuntimeException(ErrorCode.EMAIL_EXISTED);
        String email = request.getEmail();
        String otp = generateOTP();  // Tạo OTP ngẫu nhiên
        redisService.set(email, otp);
        mailService.sendOtpEmail(email, otp);  // Gửi OTP qua email
        return null;
    }


    private String generateOTP() {
        int otp = (int) (Math.random() * 1000000);
        return String.format("%04d", otp);  // Tạo OTP 6 chữ số
    }
}
