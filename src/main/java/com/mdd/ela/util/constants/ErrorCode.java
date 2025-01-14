package com.mdd.ela.util.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),

    /* 401 Unauthorized */
    AUTH_NOT_TOKEN(HttpStatus.UNAUTHORIZED, "요청 인증 실패시 : Token 값이 없습니다."),

    AUTH_END_TOKEN(HttpStatus.UNAUTHORIZED, "요청 인증 실패시 : 만료된 Token 값입니다."),

    AUTH_BAD_TOKEN(HttpStatus.UNAUTHORIZED, "요청 인증 실패시 : 잘못된 Token 값입니다."),

    AUTH_OTHER_ERROR(HttpStatus.UNAUTHORIZED, "요청 인증 실패시 : Other error"),

    /* 404 Not Found */
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Resource 또는 기능 접근 시"),

    VALIDATE(HttpStatus.UNPROCESSABLE_ENTITY, "Validate exception"),

    PERMISSION(HttpStatus.FORBIDDEN, "Permission exception"),


    /* 500 Internal Server Error */
    FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "File error : 파일이 존재하지 않습니다."),

    FILE_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "File error : 빈 파일입니다."),

    FILE_BAD_FORMAT(HttpStatus.INTERNAL_SERVER_ERROR, "File error : 잘못된 파일 또는 파일 형식입니다."),

    DATA_NOT_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : 파일 정보가 없습니다."),

    DATA_NOT_MODEL(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : 모델 정보가 없습니다."),

    DATA_NOT_SERVICE(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : 서비스 정보가 없습니다."),

    DATA_NOT_CATEGORY(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : 분류 정보가 없습니다."),

    DATA_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : 중복되는 데이터가 존재합니다."),

    DATA_OTHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB error : Other error"),

    SERVER_NOT_MODEL(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : 모델이 존재하지 않습니다."),

    SERVER_USE_GPU(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : GPU is already used"),

    SERVER_MAX_MODEL(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : 최대 모델 수 초과입니다."),

    SERVER_NOT_ACTIVE(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : Active된 모델이 아닙니다."),

    SERVER_RESTART(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : 서버 재기동 중입니다."),

    SERVER_OTHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 System Error : Other error"),

    SYSTEM_OTHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Other System error"),

    /* 503 Service Unavailable */
    SYSTEM_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "서버 문제로 인한 서비스 불가"),


    /* EMAIL */
    EMAIL_EXISTED(HttpStatus.CONFLICT,"Email đã tồn tại"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"Email không tồn tại"),

    /* PASSWORD */
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED,"Sai mật khẩu"),

    REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"Yêu cầu thất bại"),

    /* LOGIN */
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"Đăng nhập thất bại");

    private final HttpStatus httpStatus;
    private final String errorMessage;



}
