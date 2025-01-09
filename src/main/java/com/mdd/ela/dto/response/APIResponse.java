package com.mdd.ela.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.service.invoker.HttpRequestValues;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/9/2025 2:51 下午
 * @project e-learning-app
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    String message;
    Integer statusCode;
    Object result;
    Object  metaData;

    public static  APIResponse success(Object result) {
        return APIResponse.builder()
                .message("Request processed successfully")
                .statusCode(200)
                .result(result)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
    }

    public static  APIResponse success(Object result, Object metaData) {
        return APIResponse.builder()
                .message("Request processed successfully")
                .statusCode(200)
                .result(result)
                .metaData(Map.of(
                        "timestamp", LocalDateTime.now()
                )).build();
    }

    public static APIResponse error(Integer statusCode, String message) {
        return APIResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .build();
    }

    public static APIResponse error(Integer statusCode, String message, Object metaData) {
        return APIResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .metaData(metaData)
                .build();
    }
}
