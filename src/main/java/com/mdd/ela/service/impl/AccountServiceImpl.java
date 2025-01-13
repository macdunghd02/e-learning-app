package com.mdd.ela.service.impl;

import com.mdd.ela.dto.request.account.AccountResponse;
import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AccountService;
import com.mdd.ela.util.ErrorCode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder encoder) {
        this.repository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public APIResponse signUp(SignUpRequest request) {
        if (repository.existsByEmail(request.getEmail()) != 0)
            throw new AppRuntimeException(ErrorCode.EMAIL_EXISTED);
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
}
