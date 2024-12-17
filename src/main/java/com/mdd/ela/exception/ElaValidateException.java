package com.mdd.ela.exception;

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
public class ElaValidateException extends ElaRuntimeException {

    public ElaValidateException(String message) {
        super(message);
    }
    public ElaValidateException(String message, Map<String, Object> errorMap) {
        super(message, errorMap);
        this.setErrMap(errorMap);
    }

    public ElaValidateException(String message, List errorList) {
        super(message, errorList);
        this.setErrorList(errorList);
    }
}
