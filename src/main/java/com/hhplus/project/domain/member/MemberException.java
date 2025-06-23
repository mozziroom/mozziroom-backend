package com.hhplus.project.domain.member;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberException implements ExceptionInterface {
    PROVIDER_TYPE_NOT_FOUND("PROVIDE.TYPE.NOT.FOUNT","간편 로그인을 지원하지 않는 플랫폼입니다."),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "회원 권한을 찾을 수 없습니다.");
    ;

    private final String code;
    private final String message;
}
