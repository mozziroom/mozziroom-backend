package com.hhplus.project.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhplus.project.support.security.jwt.JwtAuthenticationException;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        T data,
        Error error
) {

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(HttpStatus.OK.value(), null, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), data, null);
    }

    public static <T> ApiResponse<T> fail(String errorCode, String message) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new Error(errorCode, message));
    }

    public static <T> ApiResponse<T> fail(ExceptionInterface exceptionInterface) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, Error.create(exceptionInterface));
    }

    public static <T> ApiResponse<T> fail(BaseException baseException) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, Error.create(baseException));
    }

    public static <T> ApiResponse<T> unauthorized(JwtAuthenticationException jwtAuthenticationException) {
        return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), null, Error.create(jwtAuthenticationException));
    }

    private record Error(
            String errorCode,
            String message
    ) {

        private static Error create(String errorCode, String message) {
            return new Error(errorCode, message);
        }

        private static Error create(ExceptionInterface exceptionInterface) {
            return create(exceptionInterface.getCode(), exceptionInterface.getMessage());
        }

        private static Error create(BaseException baseException) {
            return create(baseException.getCode(), baseException.getMessage());
        }

        private static Error create(JwtAuthenticationException jwtAuthenticationException) {
            return create(jwtAuthenticationException.getCode(), jwtAuthenticationException.getMessage());
        }
    }
}
