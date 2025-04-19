package com.hhplus.project.support;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    public static <T> ApiResponse<T> fail(BaseException baseException) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, Error.create(baseException));
    }

    private record Error(
            String errorCode,
            String message
    ) {

        private static Error create(String errorCode, String message) {
            return new Error(errorCode, message);
        }

        private static Error create(BaseException baseException) {
            return create(baseException.getCode(), baseException.getMessage());
        }
    }
}
