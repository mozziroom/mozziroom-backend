package com.hhplus.project.support;

import com.hhplus.project.support.security.jwt.JwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<Void> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse<Void> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = (FieldError) bindingResult.getAllErrors().getFirst();
        String errorMessage = MessageFormat.format("{0} {1}", fieldError.getField(), fieldError.getDefaultMessage());
        // TODO: 공통 로그 포멧 적용
        log.warn(errorMessage);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED.getCode(), errorMessage);
    }

    @ExceptionHandler(value = BaseException.class)
    public ApiResponse<Void> baseExceptionHandler(BaseException e) {
        // TODO: 공통 로그 포멧 적용
        log.warn(e.getMessage());
        return ApiResponse.fail(e);
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public ApiResponse<Void> jwtAuthenticationExceptionHandler(JwtAuthenticationException e) {
        log.warn(e.getMessage());
        return ApiResponse.unauthorized(e);
    }
}
