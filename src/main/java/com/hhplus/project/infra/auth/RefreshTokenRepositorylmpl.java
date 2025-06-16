package com.hhplus.project.infra.auth;

import com.hhplus.project.domain.auth.RefreshToken;
import com.hhplus.project.infra.auth.entity.RefreshTokenEntity;
import com.hhplus.project.domain.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositorylmpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> findRefreshToken(Long memberId) {
        return refreshTokenJpaRepository.findByMemberId(memberId)
                .map(RefreshTokenEntity::toDomain);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(RefreshTokenEntity.from(refreshToken)).toDomain();
    }
}

