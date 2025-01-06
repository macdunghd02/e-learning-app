package com.mdd.ela.service;

import com.mdd.ela.dto.model.Account;
import com.mdd.ela.dto.request.AuthenticationReq;
import com.mdd.ela.dto.request.IntrospectReq;
import com.mdd.ela.dto.response.DataResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/**
 * @author dungmd
 * @created 1/3/2025 7:11 下午
 * @project e-learning-app
 */

public interface AuthenticateService {
    DataResponse authenticate(AuthenticationReq req) throws JOSEException;
    DataResponse authenticate(IntrospectReq req) throws JOSEException, ParseException;
    String generateToken(Account account) throws JOSEException;
}
