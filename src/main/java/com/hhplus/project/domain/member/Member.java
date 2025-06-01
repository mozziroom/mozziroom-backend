package com.hhplus.project.domain.member;

public record Member(
        Long memberId,
        String name,
        String nickname,
        String profileImgPath,
        String email
) {

    public static Member create(String name, String nickname, String profileImgPath, String email) {
        return new Member(null, name, nickname, profileImgPath, email);
    }
}
