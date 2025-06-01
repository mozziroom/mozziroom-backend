package com.hhplus.project.domain.member;

import com.hhplus.project.support.security.oauth2.ProviderType;
import com.hhplus.project.infra.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Member save(Member member);

    Optional<MemberEntity> findByProviderTypeAndProviderId(ProviderType providerType, String providerId);
}
