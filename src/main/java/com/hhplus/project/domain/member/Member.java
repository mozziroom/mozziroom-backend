package com.hhplus.project.domain.member;


import com.hhplus.project.support.security.oauth2.ProviderType;

public record Member(
        /** 회원 id */
        Long memberId,
        /** 회원명 */
        String name,
        /** 닉네임 */
        String nickname,
        /** 이미지 url */
        String profileImageUrl,
        /** 이메일 주소 */
        String email
        String profileImgPath,
        ProviderType providerType,
        String providerId
) {

    public static Member create(
            Long memberId,
            String name,
            String nickname,
            String profileImageUrl,
            String email,
            ProviderType providerType,
            String providerId
    public static Member create(String name,
                                String nickname,
                                String profileImgPath,
                                String email,
                                ProviderType providerType,
                                String providerId
    ) {
        return new Member(memberId,
                name,
                nickname,
                profileImgPath,
                email,
                providerType,
                providerId
        );
    }
}
