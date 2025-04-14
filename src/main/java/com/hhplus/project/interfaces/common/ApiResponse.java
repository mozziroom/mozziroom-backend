package com.hhplus.project.interfaces.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {

    private final int status;
    private final String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus, message);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return of(HttpStatus.OK, message);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, "요청에 성공하였습니다.", data);
    }
}
