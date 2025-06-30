package com.hhplus.project.domain.auth;

import java.util.Optional;

public interface TokenRepository {
    Optional<RefreshToken> findRefreshToken(Long memberId);

    void save(RefreshToken refreshToken);
}
