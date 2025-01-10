package com.mdd.ela.repository;

import com.mdd.ela.dto.model.Account;
import com.mdd.ela.dto.request.account.AccountResponse;
import com.mdd.ela.dto.request.account.ChangePasswordRequest;
import com.mdd.ela.dto.request.account.SignUpRequest;
import com.mdd.ela.dto.request.account.AccountRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dungmd
 * @created 12/19/2024 9:52 AM
 * @project e-learning-app
 */
@Mapper
public interface AccountRepository{
    int signUp(SignUpRequest request);
    int changePassword(ChangePasswordRequest request);
    AccountResponse getDetail(long id);
    int existsByEmail(String email);
    AccountResponse findByEmail(String email);
    int update(AccountRequest request);
}
