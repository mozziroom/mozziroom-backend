package com.hhplus.project.infra.member;

import com.hhplus.project.domain.member.MemberRepository;
import com.hhplus.project.infra.member.entity.MemberEntity;
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
    public Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId) {
        return memberJpaRepository.findByProviderAndProviderId(provider, providerId);
    }
}
