package com.hhplus.project.domain.auth;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findRefreshToken(Long memberId);

    RefreshToken save(RefreshToken refreshToken);
}
