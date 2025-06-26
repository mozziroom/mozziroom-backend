package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.auth.TokenService;
import com.hhplus.project.domain.auth.dto.Issue;
import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
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
    private final TokenService tokenService;

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

        // 토큰 발급
        Issue.Info tokens = tokenService.issue(memberId, role);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(Duration.ofSeconds(tokenService.getRefreshExpirationSeconds()))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String redirectUri = UriComponentsBuilder
                .fromUriString(AUTH_SUCCESS_URL)
                .queryParam("accessToken", tokens.accessToken())
                .build()
                .toUriString();
        response.sendRedirect(redirectUri);
    }
}
