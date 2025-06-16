package com.hhplus.project.infra.member;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.support.security.oauth2.ProviderType;
import com.hhplus.project.infra.member.entity.MemberEntity;
import com.hhplus.project.infra.member.jpa.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.fromDomain(member)).toDomain();
    }

    @Override
    public Optional<Member> findByProviderTypeAndProviderId(ProviderType providerType, String providerId) {
        return memberJpaRepository.findByProviderTypeAndProviderId(providerType, providerId)
                .map(MemberEntity::toDomain);
    }
}
