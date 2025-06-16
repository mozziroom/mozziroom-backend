package com.hhplus.project.domain.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public record RefreshToken(
        Long refreshTokenId,
        Long memberId,
        String token,
        LocalDateTime expiredAt
) {
    public RefreshToken(Long refreshTokenId, Long memberId, String token, Date expiredAt) {
        this(refreshTokenId, memberId, token, toLocalDateTime(expiredAt));
    }

    public RefreshToken update(String token, LocalDateTime expiredAt) {
        return new RefreshToken(this.refreshTokenId, this.memberId, token, expiredAt);
    }

    private static LocalDateTime toLocalDateTime(Date expiredAt) {
        return expiredAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}