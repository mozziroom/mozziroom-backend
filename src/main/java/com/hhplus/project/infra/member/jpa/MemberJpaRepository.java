package com.hhplus.project.infra.member.jpa;

import com.hhplus.project.support.security.oauth2.ProviderType;
import com.hhplus.project.infra.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByProviderTypeAndProviderId(ProviderType providerType, String providerId);
}
