package com.hhplus.project.domain.member;


import com.hhplus.project.domain.member.oauth2.ProviderType;

public record Member() {

    public record Info(
            Long memberId,
            String name,
            String nickname,
            String profileImgPath,
            String email,
            ProviderType providerType
    ) {

        public static Member.Info create(
                Long memberId,
                String name,
                String nickname,
                String profileImgPath,
                String email,
                ProviderType providerType
        ) {
            return new Member.Info(memberId,
                    name,
                    nickname,
                    profileImgPath,
                    email,
                    providerType
            );
        }
    }
}
