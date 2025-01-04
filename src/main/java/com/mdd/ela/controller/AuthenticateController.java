package com.mdd.ela.controller;

import com.mdd.ela.dto.request.AuthenticationReq;
import com.mdd.ela.dto.request.IntrospectReq;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
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
public class AuthenticateController {
    AuthenticateService service;
    @PostMapping("login")
    ResponseEntity<Object> authenticate(@RequestBody AuthenticationReq req) throws JOSEException {
        DataResponse dataResponse = service.authenticate(req);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PostMapping("introspect")
    ResponseEntity<Object> authenticate(@RequestBody IntrospectReq req) throws JOSEException, ParseException {

        DataResponse dataResponse = service.authenticate(req);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);    }
}
