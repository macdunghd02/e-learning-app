package com.mdd.ela.service.impl;

import com.mdd.ela.dto.request.account.ChangePasswordForm;
import com.mdd.ela.dto.request.account.AccountUpdateForm;
import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.response.BaseResponse;
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
    public BaseResponse signUp(SignUpForm form) {
        try {
            if (repository.existsByEmail(form.getEmail()) != 0)
                throw new ElaValidateException("emailExisted");
            form.setPassword(encoder.encode(form.getPassword()));
            repository.signUp(form);
            return BaseResponse.simpleSuccess("success");
        }catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse changePassword(ChangePasswordForm form) {
        long modifyUserId = LoggedInUserUtil.getIdOfLoggedInUser();
        form.setNewPassword(encoder.encode(form.getNewPassword()));
        int result = repository.changePassword(form,modifyUserId);
        if (result != 1)
            throw new ElaRuntimeException("fail");
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse update(AccountUpdateForm form) {
        try {
            long modifyUserId = LoggedInUserUtil.getIdOfLoggedInUser();
            form.setModifyUserId(modifyUserId);
            int result = repository.update(form);
            if (result != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        }catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }


}
