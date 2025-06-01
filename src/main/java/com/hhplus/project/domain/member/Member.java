package com.hhplus.project.domain.member;

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
) {

    public static Member create(
            Long memberId,
            String name,
            String nickname,
            String profileImageUrl,
            String email
    ) {
        return new Member(memberId,
                name,
                nickname,
                profileImageUrl,
                email
        );
    }
}
