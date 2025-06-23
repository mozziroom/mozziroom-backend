package com.hhplus.project.domain.auth;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenException implements ExceptionInterface {

    ACCESS_TOKEN_NOT_FOUND("ACCESS.TOKEN.NOT.FOUND","Access Token을 찾을 수 없습니다."),
    ACCESS_TOKEN_EXPIRED("ACCESS.TOKEN.EXPIRED","Access Token이 만료되었습니다."),
    ;


    private final String code;
    private final String message;
}
