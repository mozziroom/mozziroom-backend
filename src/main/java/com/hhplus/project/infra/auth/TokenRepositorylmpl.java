package com.hhplus.project.infra.auth;

import com.hhplus.project.domain.auth.RefreshToken;
import com.hhplus.project.infra.auth.entity.RefreshTokenEntity;
import com.hhplus.project.domain.auth.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TokenRepositorylmpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<RefreshToken> findRefreshToken(Long memberId) {
        return tokenJpaRepository.findByMemberId(memberId)
                .map(RefreshTokenEntity::toDomain);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return tokenJpaRepository.save(RefreshTokenEntity.from(refreshToken)).toDomain();
    }
}

