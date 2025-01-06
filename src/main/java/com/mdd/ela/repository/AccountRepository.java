package com.mdd.ela.repository;

import com.mdd.ela.dto.model.Account;
import com.mdd.ela.dto.request.account.ChangePasswordForm;
import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.request.account.AccountUpdateForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dungmd
 * @created 12/19/2024 9:52 AM
 * @project e-learning-app
 */
@Mapper
public interface AccountRepository{
    int signUp(SignUpForm form);
    int changePassword(ChangePasswordForm form, long modifyUserId);
    int existsByEmail(String email);
    Account findByEmailAndRole(String email, String role);
    int update(AccountUpdateForm form);
}
