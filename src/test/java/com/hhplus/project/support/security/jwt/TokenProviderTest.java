package com.hhplus.project.support.security.jwt;

import com.hhplus.project.domain.auth.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest {

    String ACCESS_SECRET = "cdcq0Qt7yrsHkvI1uN2wA9nE1kBm4c5s0WCbaiHS3nY=";
    String REFRESH_SECRET = "vTmdmU+aQ6U14Psw0uAsgBi/TC8+6uk3Wc/kPnfTjSI=";
    Long MEMBER_ID = 1L;
    String ROLE = "ROLE_USER";

    @Test
    @DisplayName("Access Token 을 생성한 뒤, 토큰을 파싱해서 memberId와 권한 정보를 얻는다.")
    void createAccessToken() {
        // given
        TokenProvider tokenProvider = create(10000, 10000);

        // when
        String token = tokenProvider.issueAccessToken(MEMBER_ID, ROLE);

        // then
        assertThat(tokenProvider.validateAccessToken(token)).isTrue();
        assertThat(tokenProvider.getMemberIdOfAccessToken(token)).isEqualTo(MEMBER_ID);
        assertThat(tokenProvider.getRoleOfAccessToken(token)).isEqualTo(ROLE);
    }

    @Test
    @DisplayName("Refresh Token 을 생성한 뒤, 토큰을 파싱해서 memberId와 권한 정보를 얻는다.")
    void createRefreshToken() {
        // given
        TokenProvider tokenProvider = create(10000, 10000);

        // when
        RefreshToken refreshToken = tokenProvider.issueRefreshToken(MEMBER_ID, ROLE);

        // then
        String token = refreshToken.token();
        assertThat(tokenProvider.validateRefreshToken(token)).isTrue();
        assertThat(tokenProvider.getMemberIdOfRefreshToken(token)).isEqualTo(1L);
        assertThat(tokenProvider.getRoleOfRefreshToken(token)).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("Access Token 이 만료되었으면 false 를 반환한다")
    void validateAccessTokenReturnFalse() throws InterruptedException {
        // given
        TokenProvider tokenProvider = create(1000, 10000);

        // when
        String token = tokenProvider.issueAccessToken(MEMBER_ID, ROLE);
        Thread.sleep(1100);

        // then
        assertThat(tokenProvider.validateAccessToken(token)).isFalse();
    }

    @Test
    @DisplayName("Refresh Token 이 만료되었으면 false 를 반환한다")
    void validateRefreshTokenReturnFalse() throws InterruptedException {
        // given
        TokenProvider tokenProvider = create(1000, 1000);

        // when
        RefreshToken refreshToken = tokenProvider.issueRefreshToken(MEMBER_ID, ROLE);
        Thread.sleep(1100);

        // then
        assertThat(tokenProvider.validateRefreshToken(refreshToken.token())).isFalse();
    }

    private TokenProvider create(long accessExpiration, long refreshExpiration) {
        return new TokenProvider(ACCESS_SECRET, accessExpiration, REFRESH_SECRET, refreshExpiration);
    }
}