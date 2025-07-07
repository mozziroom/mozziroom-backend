package com.hhplus.project.interfaces.auth;

import com.hhplus.project.domain.auth.TokenService;
import com.hhplus.project.interfaces.auth.dto.Reissue;
import com.hhplus.project.support.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    @GetMapping("/auto-login")
    public ApiResponse<Void> login() {
        return ApiResponse.ok();
    }

    @PostMapping("/reissue")
    public ApiResponse<Reissue.Response> reissue(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        return ApiResponse.ok(Reissue.Response.from(tokenService.reissue(refreshToken, response)));
    }
}
