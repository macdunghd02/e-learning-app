package com.mdd.ela.exception;


import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.util.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
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
    public ResponseEntity<BaseResponse> handleException(HttpServletRequest request, Exception ex) {
        log.error("Exception: ", ex);
        return this.toResponseEntity(ErrorCode.SYSTEM_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(ElaValidateException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(ElaValidateException ex) {
        return this.toResponseEntity(ErrorCode.VALIDATE, ex.getMessage());
    }

    @ExceptionHandler(ElaRuntimeException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(ElaRuntimeException ex) {
        return this.toResponseEntity(ErrorCode.SYSTEM_OTHER_ERROR, ex.getMessage());
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        return this.toResponseEntity(ErrorCode.INVALID_PARAMETER, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        return this.toResponseEntity(ErrorCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(HttpServletRequest request, HttpServerErrorException ex) {
        return this.toResponseEntity(ErrorCode.SYSTEM_OTHER_ERROR, ex.getMessage());
    }

//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//
//    public ResponseEntity<BaseResponse> handleHttpServerErrorException(HttpServletRequest request, MaxUploadSizeExceededException ex) {
//        return this.toResponseEntity(ErrorCode.VALIDATE, "filesNotAllowedToOver20Mb");
//    }

//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<BaseResponse> handleUnauthorizedException(HttpServletRequest request, UnauthorizedException ex) {
//        return this.toResponseEntity(ErrorCode.AUTH_BAD_TOKEN, ex.getMessage());
//    }

    private ResponseEntity<BaseResponse> toResponseEntity(ErrorCode errorCode, String errorMessage, List<Object> errorList, Map<String, Object> errMap) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .isError(true)
                        .message(errorMessage)
                        .errorList(errorList)
                        .errMap(errMap)
                        .build()
                );
    }

    private ResponseEntity<BaseResponse> toResponseEntity(ErrorCode errorCode, String errorMessage) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .isError(true)
                        .message(errorMessage)
                        .build()
                );
    }


    private ResponseEntity<BaseResponse> toResponseEntity(ErrorCode errorCode, String errorMessage, List<Object> errors) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .isError(true)
                        .message(errorMessage)
                        .errorList(errors)
                        .build()
                );
    }

}
