package com.mdd.ela.service;

import com.mdd.ela.dto.account.ChangePasswordRequest;
import com.mdd.ela.dto.account.AccountRequest;
import com.mdd.ela.dto.account.SendOtpRequest;
import com.mdd.ela.dto.account.SignUpRequest;
import com.mdd.ela.model.base.APIResponse;

/**
 * @author dungmd
 * @created 12/19/2024 9:51 AM
 * @project e-learning-app
 */

public interface AccountService {
    APIResponse signUp(SignUpRequest request);
    APIResponse getDetail(long id);
    APIResponse changePassword(ChangePasswordRequest request);
    APIResponse update(AccountRequest request);
    APIResponse sendOtp(SendOtpRequest request);
}
