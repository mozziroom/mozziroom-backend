package com.hhplus.project.infra.member;

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
    public MemberEntity save(MemberEntity memberEntity) {
        return memberJpaRepository.save(memberEntity);
    }

    @Override
    public Optional<MemberEntity> findByProviderTypeAndProviderId(ProviderType providerType, String providerId) {
        return memberJpaRepository.findByProviderTypeAndProviderId(providerType, providerId);
    }
}
