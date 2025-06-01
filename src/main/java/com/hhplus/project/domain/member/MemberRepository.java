package com.hhplus.project.domain.member;

import com.hhplus.project.support.security.oauth2.ProviderType;
import com.hhplus.project.infra.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<MemberEntity> findByProviderTypeAndProviderId(ProviderType providerType, String providerId);
}
