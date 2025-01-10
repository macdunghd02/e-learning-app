package com.mdd.ela.service.impl;

import com.mdd.ela.dto.request.AuthenticationRequest;
import com.mdd.ela.dto.request.IntrospectRequest;
import com.mdd.ela.dto.request.account.AccountResponse;
import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.dto.response.auth.AuthenticationRes;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.AccountRepository;
import com.mdd.ela.service.AuthenticateService;
import com.mdd.ela.util.ErrorCode;
import com.mdd.ela.util.JwtUtil;
import com.nimbusds.jose.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/3/2025 7:14 下午
 * @project e-learning-app
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    AccountRepository repository;
    JwtUtil jwtUtil;

    @Override
    public APIResponse authenticate(AuthenticationRequest req) throws JOSEException {
            AccountResponse account = repository.findByEmail(req.getEmail());
            if (account == null) {
                throw new AppRuntimeException(ErrorCode.EMAIL_NOT_FOUND);
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authenticated = passwordEncoder.matches(req.getPassword(), account.getPassword());
            if (!authenticated)
                throw new AppRuntimeException(ErrorCode.WRONG_PASSWORD);


            AuthenticationRes authenticationRes = AuthenticationRes.builder()
                    .refreshToken(jwtUtil.generateRefreshToken(req.getEmail(), account.getId()))
                    .token(jwtUtil.generateAccessToken(req.getEmail(), account.getId()))
                    .build();

            Map<String, Object> res = new HashMap<>();
            res.put("token", authenticationRes);
            res.put("user", account);
            return APIResponse.success(res);
    }

    @Override
    public APIResponse refreshToken(String refreshToken) throws JOSEException, ParseException {
            if (jwtUtil.validateToken(refreshToken)) {
                String email = jwtUtil.extractEmail(refreshToken);
                AccountResponse account = repository.findByEmail(email);
                AuthenticationRes authenticationRes = AuthenticationRes.builder()
                        .refreshToken(jwtUtil.generateRefreshToken(email, account.getId()))
                        .token(jwtUtil.generateAccessToken(email, account.getId()))
                        .build();
                Map<String, Object> res = new HashMap<>();
                res.put("token", authenticationRes);
                res.put("user", account);
                return APIResponse.success(res);
            }
            throw new AppRuntimeException(ErrorCode.LOGIN_FAIL);
}}
