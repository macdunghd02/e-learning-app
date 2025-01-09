package com.mdd.ela.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdd.ela.util.ErrorCode;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
@Setter
@AllArgsConstructor
public class ElaRuntimeException extends RuntimeException {
    private final Integer statusCode;

    public ElaRuntimeException(String message) {
        super(message);
        this.statusCode = 500;
    }
    public ElaRuntimeException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.statusCode = errorCode.getHttpStatus().value();
    }
}
