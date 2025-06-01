package com.hhplus.project.fixture;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.infra.member.entity.MemberEntity;
import com.hhplus.project.support.security.oauth2.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberFixture {

    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = {Exception.class})
    public Member create() {
        return memberRepository.save(Member.create("김회원",
                "멤바",
                "/image/profile.jpg",
                "member@google.com",
                ProviderType.GOOGLE,
                "google_id"));
    }

    public static MemberEntity createMember() {
        return MemberEntity.builder()
                .name("김회원")
                .nickname("멤바")
                .profileImgPath("/image/profile.jpg")
                .email("member@google.com")
                .build();
    }
}
