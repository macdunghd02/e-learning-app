package com.mdd.ela.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("error_list")
    private List errorList;

    @JsonProperty("error_map")
    private Map<String, Object> errMap;
    public ElaRuntimeException(Throwable e) {
        super(e);
    }

    public ElaRuntimeException(String message) {
        super(message);
    }

    public ElaRuntimeException(String message, Map<String, Object> errMap) {
        super(message);
        this.errMap = Map.copyOf(errMap);
    }
    public ElaRuntimeException(String message, List<?> errorList) {
        super(message);
        this.errorList = List.copyOf(errorList);
    }
}
