package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.auth.RefreshToken;
import com.hhplus.project.domain.auth.RefreshTokenService;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oAuth2User.getMemberId();

        // JWT 발급
        String accessToken = tokenProvider.generateAccessToken(memberId);
        String refreshToken = tokenProvider.generateRefreshToken(memberId);
        Date expiredAt = tokenProvider.getExpiration(refreshToken);

        RefreshToken refreshTokenDto = new RefreshToken(null, memberId, refreshToken, expiredAt);

        refreshTokenService.saveOrUpdate(refreshTokenDto);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(Duration.ofSeconds(refreshExpiration / 1000L))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String redirectUri = UriComponentsBuilder
                .fromUriString(getBaseUrl(request))
                .path("/login/")
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();
        response.sendRedirect(redirectUri);
    }

    private String getBaseUrl(HttpServletRequest request) {
        return UriComponentsBuilder.newInstance()
                .scheme(request.getScheme())
                .host(request.getServerName())
                .port(request.getServerPort())
                .build()
                .toUriString();
    }
}
