package com.hhplus.project.infra.auth.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.auth.RefreshToken;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    private RefreshTokenEntity(Long refreshTokenId, Long memberId, String token, LocalDateTime expiredAt) {
        this.refreshTokenId = refreshTokenId;
        this.memberId = memberId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public static RefreshTokenEntity create(Long refreshTokenId, Long memberId, String token, LocalDateTime expiredAt) {
        return new RefreshTokenEntity(refreshTokenId, memberId, token, expiredAt);
    }

    public static RefreshTokenEntity from(RefreshToken refreshToken) {
        return create(refreshToken.refreshTokenId(), refreshToken.memberId(), refreshToken.token(), refreshToken.expiredAt());
    }

    public RefreshToken toDomain() {
        return new RefreshToken(refreshTokenId, memberId, token, expiredAt);
    }
}
