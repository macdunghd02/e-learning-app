package com.mdd.ela.controller;

import com.mdd.ela.dto.auth.AuthenticationRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.dto.auth.RefreshTokenRequest;
import com.mdd.ela.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author dungmd
 * @created 1/3/2025 7:10 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@Tag(name = "Authentication")
public class AuthenticateController {
    AuthenticateService service;

    @Operation(summary = "Login")
    @PostMapping("login")
    ResponseEntity<APIResponse> login(@RequestBody AuthenticationRequest req) throws JOSEException {
        APIResponse response = service.authenticate(req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Refresh token")
    @PostMapping("refresh-token")
    ResponseEntity<APIResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws JOSEException, ParseException {
        APIResponse response = service.refreshToken(request.getRefreshToken());
        return new ResponseEntity<>(response, HttpStatus.OK);    }
}
