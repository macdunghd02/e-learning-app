package com.mdd.ela.exception;

import com.mdd.ela.util.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
@Setter
public class ElaValidateException extends RuntimeException {
    private final Integer statusCode;

    public ElaValidateException(String message) {
        super(message);
        this.statusCode = 500;
    }
    public ElaValidateException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.statusCode = errorCode.getHttpStatus().value();
    }
}
