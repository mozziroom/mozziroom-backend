package com.hhplus.project.fixture;

import com.hhplus.project.infra.member.entity.MemberEntity;

import java.time.LocalDateTime;

public class MemberFixture {

    public static MemberEntity createMember() {
        return new MemberEntity(
                "김회원",
                "멤바",
                "/image/profile.jpg",
                "member@google.com",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
