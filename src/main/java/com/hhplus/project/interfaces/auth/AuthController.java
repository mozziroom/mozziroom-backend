package com.hhplus.project.interfaces.auth;

import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller("/auth")
public class AuthController {

    private final TokenProvider tokenProvider;

    @PostMapping("/reissue")
    public ResponseEntity<Map<String, String>> reissue(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        if (!tokenProvider.validateRefreshToken(refreshToken)) {
            ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(0)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long memberId = tokenProvider.getMemberId(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(memberId);

        Map<String, String> body = new HashMap<>();
        body.put("accessToken", accessToken);
        return ResponseEntity.ok(body);
    }
}
