package com.mdd.ela.service.impl;

import com.mdd.ela.dto.request.account.AccountResponse;
import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.exception.ElaValidateException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AccountService;
import com.mdd.ela.util.ErrorCode;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author dungmd
 * @created 12/19/2024 9:50 AM
 * @project e-learning-app
 */

@Service
@Transactional(rollbackFor = ElaRuntimeException.class)
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
        try {
            if (repository.existsByEmail(request.getEmail()) != 0)
                throw new ElaRuntimeException(ErrorCode.EMAIL_EXISTED);
            request.setPassword(encoder.encode(request.getPassword()));
            repository.signUp(request);
            AccountResponse accountResponse = repository.getDetail(request.getId());
            return APIResponse.success(accountResponse);
        } catch (ElaRuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public APIResponse getDetail(long id) {
        try {
            AccountResponse response = repository.getDetail(id);
            return APIResponse.success(response);
        } catch (ElaRuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public APIResponse changePassword(ChangePasswordRequest request) {
        try {
            AccountResponse currentAcc = repository.getDetail(request.getId());
            String en = encoder.encode(request.getOldPassword());
            if(!encoder.matches(request.getOldPassword(), currentAcc.getPassword()))
                throw new ElaRuntimeException(ErrorCode.WRONG_PASSWORD);
            request.setNewPassword(encoder.encode(request.getNewPassword()));
            int result = repository.changePassword(request);
            if (result != 1)
                throw new ElaRuntimeException("Request fail");
            return APIResponse.success(null);
        } catch (ElaRuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public APIResponse update(AccountRequest request) {
        try {
            int result = repository.update(request);
            AccountResponse accountResponse = repository.getDetail(request.getId());
            if (result != 1)
                throw new ElaRuntimeException("Request fail");
            return APIResponse.success(accountResponse);
        } catch (ElaRuntimeException e) {
            throw e;
        } catch (Exception e){
            throw new ElaRuntimeException(e.getMessage());
        }
    }
}
