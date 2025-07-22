package com.hhplus.project.domain.auth;

import com.hhplus.project.domain.auth.dto.Issue;
import com.hhplus.project.domain.auth.dto.Reissue;
import com.hhplus.project.support.security.jwt.JwtAuthenticationException;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public Issue.Info issue(Long memberId, String role) {
        String accessToken = tokenProvider.issueAccessToken(memberId, role);
        RefreshToken refreshTokenDto = tokenProvider.issueRefreshToken(memberId, role);

        RefreshToken refreshToken = tokenRepository.findRefreshToken(memberId)
                .map(token -> token.update(refreshTokenDto.token(), refreshTokenDto.expiredAt()))
                .orElse(refreshTokenDto);

        tokenRepository.save(refreshToken);

        return new Issue.Info(accessToken, refreshToken.token());
    }

    public Reissue.Info reissue(String refreshToken, HttpServletResponse response) {
        if(refreshToken == null) {
            throw new JwtAuthenticationException(TokenException.REFRESH_TOKEN_NOT_FOUND);
        }
        if (tokenProvider.isInvalidateRefreshToken(refreshToken)) {
            ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(0)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            throw new JwtAuthenticationException(TokenException.REFRESH_TOKEN_EXPIRED);
        }

        Long memberId = tokenProvider.getMemberIdOfRefreshToken(refreshToken);
        String role = tokenProvider.getRoleOfRefreshToken(refreshToken);
        String accessToken = tokenProvider.issueAccessToken(memberId, role);

        return new Reissue.Info(accessToken);
    }

    public Long getMemberIdOfAccessToken(String accessToken) {
        return tokenProvider.getMemberIdOfAccessToken(accessToken);
    }

    public long getRefreshExpirationSeconds() {
        return tokenProvider.convertRefreshExpirationToSeconds();
    }

    // Authorization 헤더 문자열에서 Bearer 토큰 추출
    public String extractTokenFromHeader(String authorization) {
        return tokenProvider.extractTokenFromHeader(authorization);
    }
}
