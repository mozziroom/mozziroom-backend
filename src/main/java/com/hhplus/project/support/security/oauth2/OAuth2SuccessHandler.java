package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // JWT 발급
        String token = tokenProvider.createToken(oAuth2User.getMemberId());

        String redirectUri = UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .queryParam("token", token)
                .build()
                .toUriString();

        response.sendRedirect(redirectUri);
    }
}
