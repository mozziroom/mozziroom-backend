package com.hhplus.project.interfaces.auth;

import com.hhplus.project.domain.auth.TokenException;
import com.hhplus.project.interfaces.auth.dto.Reissue;
import com.hhplus.project.support.ApiResponse;
import com.hhplus.project.support.security.jwt.JwtAuthenticationException;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final TokenProvider tokenProvider;

    @GetMapping("/auto-login")
    public ApiResponse<Void> login() {
        return ApiResponse.ok();
    }

    @PostMapping("/reissue")
    public ApiResponse<Reissue.Response> reissue(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
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
        String accessToken = tokenProvider.generateAccessToken(memberId, role);

        return ApiResponse.ok(new Reissue.Response(accessToken));
    }
}
