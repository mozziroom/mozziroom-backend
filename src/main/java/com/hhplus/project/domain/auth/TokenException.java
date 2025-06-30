package com.hhplus.project.domain.auth;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenException implements ExceptionInterface {

    ACCESS_TOKEN_EXPIRED("ACCESS.TOKEN.EXPIRED","Access Token이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND("REFRESH.TOKEN.NOT.FOUND","Refresh Token을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED("REFRESH.TOKEN.EXPIRED","Refresh Token이 만료되었습니다."),
    ;

    private final String code;
    private final String message;
}
