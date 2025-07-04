package com.hhplus.project.interfaces.auth;

import com.hhplus.project.support.ApiResponse;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {

    private final TokenProvider tokenProvider;

    @GetMapping("/auto-login")
    public ApiResponse<Void> login() {
        return ApiResponse.ok();
    }

    @PostMapping("/reissue")
    public ResponseEntity<Map<String, String>> reissue(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if(refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (tokenProvider.isInvalidateRefreshToken(refreshToken)) {
            ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .sameSite("None")
                    .maxAge(0)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long memberId = tokenProvider.getMemberIdOfRefreshToken(refreshToken);
        String role = tokenProvider.getRoleOfRefreshToken(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(memberId, role);

        Map<String, String> body = new HashMap<>();
        body.put("accessToken", accessToken);
        return ResponseEntity.ok(body);
    }
}
