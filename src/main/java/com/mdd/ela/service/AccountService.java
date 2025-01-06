package com.mdd.ela.service;

import com.mdd.ela.dto.request.account.ChangePasswordForm;
import com.mdd.ela.dto.request.account.AccountUpdateForm;
import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.response.BaseResponse;

/**
 * @author dungmd
 * @created 12/19/2024 9:51 AM
 * @project e-learning-app
 */

public interface AccountService {
    BaseResponse signUp(SignUpForm form);
    BaseResponse changePassword(ChangePasswordForm form);
    BaseResponse update(AccountUpdateForm form);

}
