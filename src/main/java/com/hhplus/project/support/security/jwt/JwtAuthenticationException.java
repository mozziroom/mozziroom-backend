package com.hhplus.project.support.security.jwt;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private final String code;
    private final String message;

    public JwtAuthenticationException(ExceptionInterface exceptionInterface) {
        super(exceptionInterface.getMessage());
        this.code = exceptionInterface.getCode();
        this.message = exceptionInterface.getMessage();
    }
}
