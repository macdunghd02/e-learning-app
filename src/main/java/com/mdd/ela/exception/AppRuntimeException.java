package com.mdd.ela.exception;

import com.mdd.ela.util.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
@Setter
@AllArgsConstructor
public class AppRuntimeException extends RuntimeException {
    private final Integer statusCode;

    public AppRuntimeException(String message) {
        super(message);
        this.statusCode = 500;
    }
    public AppRuntimeException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.statusCode = errorCode.getHttpStatus().value();
    }
}
