package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProviderType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String provider;

    public static ProviderType from(String provider) {
        return Arrays.stream(ProviderType.values())
                .filter(type -> type.getProvider().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new BaseException(MemberException.PROVIDER_TYPE_NOT_FOUND));
    }
}
