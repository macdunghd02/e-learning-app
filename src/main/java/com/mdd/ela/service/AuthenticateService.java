package com.mdd.ela.service;

import com.mdd.ela.dto.auth.AuthenticationRequest;
import com.mdd.ela.model.base.APIResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/**
 * @author dungmd
 * @created 1/3/2025 7:11 下午
 * @project e-learning-app
 */

public interface AuthenticateService {
    APIResponse authenticate(AuthenticationRequest req) throws JOSEException;
    APIResponse refreshToken(String refreshToken) throws JOSEException, ParseException;
}
