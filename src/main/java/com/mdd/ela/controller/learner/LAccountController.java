package com.mdd.ela.controller.learner;

import com.mdd.ela.dto.request.account.ChangePasswordForm;
import com.mdd.ela.dto.request.account.UpdateAccountForm;
import com.mdd.ela.dto.request.account.SignUpForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author dungmd
 * @created 12/24/2024 3:04 PM
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/learner/account")
public class LAccountController {
    @Autowired
    AccountService service;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpForm form){
        BaseResponse baseResponse = service.signUp(form);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordForm form){
        BaseResponse baseResponse = service.changePassword(form);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateAccountForm form){
        BaseResponse baseResponse = service.update(form);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
