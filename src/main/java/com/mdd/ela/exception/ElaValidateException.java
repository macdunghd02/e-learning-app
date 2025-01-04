package com.mdd.ela.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ValidationException;
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
public class ElaValidateException extends ValidationException {

    @JsonProperty("error_list")
    private List errorList;

    @JsonProperty("error_map")
    private Map<String, Object> errMap;
    public ElaValidateException(Throwable e) {
        super(e);
    }

    public ElaValidateException(String message) {
        super(message);
    }

    public ElaValidateException(String message, Map<String, Object> errMap) {
        super(message);
        this.errMap = Map.copyOf(errMap);
    }
    public ElaValidateException(String message, List<?> errorList) {
        super(message);
        this.errorList = List.copyOf(errorList);
    }
}
