package com.mdd.ela.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BaseResponse {
    @JsonProperty(value = "is_error")
    private boolean isError;
    @JsonProperty("message")
    String message;
    @JsonProperty("error_list")
    private List errorList;
    @JsonProperty("error")
    private Map<String, Object> errMap;


    public static BaseResponse simpleSuccess(String message) {
        return BaseResponse.builder().isError(false).message(message).build();
    }

    public static BaseResponse simpleFail(String message) {
        return BaseResponse.builder().isError(true).message(message).build();
    }
}
