package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private static final String CLIENT_BASE_URL = "http://localhost:3000";
    private static final String AUTH_SUCCESS_URL = CLIENT_BASE_URL + "/login/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oAuth2User.getMemberId();
        String role = oAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new BaseException(MemberException.ROLE_NOT_FOUND))
                .getAuthority();

        // JWT 발급
        String accessToken = tokenProvider.generateAccessToken(memberId, role);
        String refreshToken = tokenProvider.generateRefreshToken(memberId, role);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(Duration.ofSeconds(tokenProvider.convertRefreshExpirationToSeconds()))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String redirectUri = UriComponentsBuilder
                .fromUriString(AUTH_SUCCESS_URL)
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();
        response.sendRedirect(redirectUri);
    }
}
