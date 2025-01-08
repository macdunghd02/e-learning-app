package com.mdd.ela.service;

import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;

/**
 * @author dungmd
 * @created 12/19/2024 9:51 AM
 * @project e-learning-app
 */

public interface AccountService {
    BaseResponse signUp(SignUpRequest request);
    DataResponse getDetail(long id);
    BaseResponse changePassword(ChangePasswordRequest request, long id);
    BaseResponse update(AccountRequest request,long id);

}
