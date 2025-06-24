package com.hhplus.project.support.security.jwt;

import com.hhplus.project.domain.auth.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public static final List<String> EXCLUDE_PATH = List.of(
            "/", "/login", "/auth/reissue", "/auth/success"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_PATH.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = resolveToken(request);

            if(accessToken != null) {
                if(tokenProvider.isInvalidAccessToken(accessToken)) {
                    throw new JwtAuthenticationException(TokenException.ACCESS_TOKEN_EXPIRED);
                }

                Long memberId = tokenProvider.getMemberIdOfAccessToken(accessToken);
                String role = tokenProvider.getRoleOfAccessToken(accessToken);

                // 로그인 유지 처리
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch(AuthenticationException e) {
            SecurityContextHolder.clearContext();
            jwtAuthenticationEntryPoint.commence(request, response, e);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
