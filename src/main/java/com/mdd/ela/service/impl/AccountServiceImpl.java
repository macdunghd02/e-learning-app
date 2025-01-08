package com.mdd.ela.service.impl;

import com.mdd.ela.dto.request.account.AccountResponse;
import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.exception.ElaValidateException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AccountService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
    public BaseResponse signUp(SignUpRequest request) {
        try {
            if (repository.existsByEmail(request.getEmail()) != 0)
                throw new ElaValidateException("emailExisted");
            request.setPassword(encoder.encode(request.getPassword()));
            repository.signUp(request);
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse getDetail(long id) {
        try {
            AccountResponse response = repository.getDetail(id);
            return DataResponse.builder().data(response).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse changePassword(ChangePasswordRequest request, long id) {
        try {
            AccountResponse currentAcc = repository.getDetail(id);
            if(!Objects.equals(encoder.encode(request.getOldPassword()), currentAcc.getPassword()))
                throw new ElaRuntimeException("wrongPassword");
            request.setNewPassword(encoder.encode(request.getNewPassword()));
            int result = repository.changePassword(request, id);
            if (result != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        }catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse update(AccountRequest request, long id) {
        try {
            request.setModifyUserId(id);
            int result = repository.update(request);
            if (result != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }


}
