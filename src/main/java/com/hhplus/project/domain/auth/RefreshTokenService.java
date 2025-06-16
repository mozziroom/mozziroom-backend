package com.hhplus.project.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveOrUpdate(RefreshToken refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenRepository.findRefreshToken(refreshTokenDto.memberId())
                .map(token -> token.update(refreshTokenDto.token(), refreshTokenDto.expiredAt()))
                .orElse(refreshTokenDto);

        refreshTokenRepository.save(refreshToken);
    }
}
