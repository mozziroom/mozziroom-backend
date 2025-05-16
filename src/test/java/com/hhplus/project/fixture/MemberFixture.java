package com.hhplus.project.fixture;

import com.hhplus.project.infra.member.entity.MemberEntity;

import java.time.LocalDateTime;

public class MemberFixture {

    public static MemberEntity createMember() {
        return MemberEntity.builder()
                .name("김회원")
                .nickname("멤바")
                .profileImgPath("/image/profile.jpg")
                .email("member@google.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
