package com.hhplus.project.domain.member;


import com.hhplus.project.support.security.oauth2.ProviderType;

public record Member(
        Long memberId,
        String name,
        String nickname,
        String profileImgPath,
        String email,
        ProviderType providerType,
        String providerId
) {

    public static Member create(String name,
                                String nickname,
                                String profileImgPath,
                                String email,
                                ProviderType providerType,
                                String providerId
    ) {
        return new Member(null,
                name,
                nickname,
                profileImgPath,
                email,
                providerType,
                providerId
        );
    }
}
