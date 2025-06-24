package com.hhplus.project.support.security.jwt;

import com.hhplus.project.domain.auth.RefreshToken;
import com.hhplus.project.domain.auth.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    private final String accessSecret;
    private final long accessExpiration;
    private final String refreshSecret;
    private final long refreshExpiration;
    private final TokenService tokenService;

    public TokenProvider (
            @Value("${jwt.access.secret}") String accessSecret,
            @Value("${jwt.access.expiration}") long accessExpiration,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            @Value("${jwt.refresh.expiration}") long refreshExpiration,
            TokenService tokenService
    ) {
        this.accessSecret = accessSecret;
        this.accessExpiration = accessExpiration;
        this.refreshSecret = refreshSecret;
        this.refreshExpiration = refreshExpiration;
        this.tokenService = tokenService;
    }

    public String generateAccessToken(Long memberId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessExpiration);

        return Jwts.builder()
                .setClaims(createClaims(memberId, role))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(accessSecret))
                .compact();
    }

    public String generateRefreshToken(Long memberId, String role) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + refreshExpiration);

        String refreshToken = Jwts.builder()
                .setClaims(createClaims(memberId, role))
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(getSigningKey(refreshSecret))
                .compact();

        tokenService.saveOrUpdate(RefreshToken.create(memberId, refreshToken, expiredAt));

        return refreshToken;
    }

    private static Claims createClaims(Long memberId, String role) {
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(memberId));
        claims.put("role", role);
        return claims;
    }

    public Long getMemberIdOfAccessToken(String accessToken) {
        Claims claims = parseClaims(accessSecret, accessToken);
        return Long.parseLong(claims.getSubject());
    }

    public Long getMemberIdOfRefreshToken(String refreshToken) {
        Claims claims = parseClaims(refreshSecret, refreshToken);
        return Long.parseLong(claims.getSubject());
    }

    public String getRoleOfAccessToken(String accessToken) {
        Claims claims = parseClaims(accessSecret, accessToken);
        return claims.get("role").toString();
    }

    public String getRoleOfRefreshToken(String refreshToken) {
        Claims claims = parseClaims(refreshSecret, refreshToken);
        return claims.get("role").toString();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(accessSecret))
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException | JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(refreshSecret))
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (ExpiredJwtException | JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isInvalidateRefreshToken(String refreshToken) {
        return !validateRefreshToken(refreshToken);
    }

    public boolean isInvalidAccessToken(String accessToken) {
        return !validateAccessToken(accessToken);
    }

    public long convertRefreshExpirationToSeconds() {
        return refreshExpiration / 1000L;
    }

    private Key getSigningKey(String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims parseClaims(String secret, String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
