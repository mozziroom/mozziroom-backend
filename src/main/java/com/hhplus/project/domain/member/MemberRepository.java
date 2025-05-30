package com.hhplus.project.domain.member;

import com.hhplus.project.infra.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId);
}