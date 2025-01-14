package com.mdd.ela.exception;


import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.util.constants.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleException(HttpServletRequest request, Exception ex) {
        APIResponse response = APIResponse.builder()
                .message(ex.getMessage())
                .statusCode(500)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
        return this.toResponseEntity(ErrorCode.SYSTEM_OTHER_ERROR, response);
    }


    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<APIResponse> handleHttpServerErrorException(AppRuntimeException ex) {
        APIResponse response = APIResponse.builder()
                .message(ex.getMessage())
                .statusCode(ex.getStatusCode())
                .result(null)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                ))
                .build();
        return this.toResponseEntity(ex.getStatusCode(), response);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        APIResponse response = APIResponse.builder()
                .message(ex.getMessage())
                .statusCode(400)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
        return this.toResponseEntity(ErrorCode.INVALID_PARAMETER, response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIResponse> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        APIResponse response = APIResponse.builder()
                .message(ex.getMessage())
                .statusCode(404)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
        return this.toResponseEntity(ErrorCode.NOT_FOUND, response);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<APIResponse> handleHttpServerErrorException(HttpServletRequest request, HttpServerErrorException ex) {
        APIResponse response = APIResponse.builder()
                .message(ex.getMessage())
                .statusCode(500)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
        return this.toResponseEntity(ErrorCode.SYSTEM_OTHER_ERROR, response);
    }

    private ResponseEntity<APIResponse> toResponseEntity(ErrorCode errorCode, APIResponse response) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
    private ResponseEntity<APIResponse> toResponseEntity(Integer statusCode, APIResponse response) {
        return ResponseEntity
                .status(statusCode)
                .body(response);
    }
}
