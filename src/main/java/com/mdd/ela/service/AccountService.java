package com.mdd.ela.service;

import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import org.apache.poi.ss.formula.functions.T;

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

}
