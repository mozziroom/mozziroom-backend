package com.hhplus.project.domain.member;


import com.hhplus.project.support.security.oauth2.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findMemberByProviderTypeAndProviderId(ProviderType providerType, String providerId) {
        return memberRepository.findByProviderTypeAndProviderId(providerType, providerId);
    }
}
