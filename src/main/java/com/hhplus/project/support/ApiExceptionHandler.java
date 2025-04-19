package com.hhplus.project.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Void> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = (FieldError) bindingResult.getAllErrors().getFirst();
        String errorMessage = MessageFormat.format("{0} {1}", fieldError.getField(), fieldError.getDefaultMessage());
        // TODO: 공통 로그 포멧 적용
        log.warn(errorMessage);
        return ApiResponse.fail(BaseExceptionEnum.EXCEPTION_ISSUED.getCode(), errorMessage);
    }

    public ApiResponse<Void> baseExceptionHandler(BaseException e) {
        // TODO: 공통 로그 포멧 적용
        log.warn(e.getMessage());
        return ApiResponse.fail(e);
    }
}
