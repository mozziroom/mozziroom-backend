package com.hhplus.project.infra.member;

import com.hhplus.project.infra.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId);
}
