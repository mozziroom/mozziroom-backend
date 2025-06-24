package com.hhplus.project.support.security.jwt;

import com.hhplus.project.domain.auth.RefreshToken;
import com.hhplus.project.domain.auth.TokenService;
import com.hhplus.project.fixture.TokenProviderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    @Mock
    private TokenService tokenService;

    @Test
    @DisplayName("Access Token 을 생성한 뒤, 토큰을 파싱해서 memberId와 권한 정보를 얻는다.")
    void createAccessToken() {
        // given
        TokenProvider tokenProvider = TokenProviderFixture.create(10000, 10000, tokenService);
        Long memberId = 1L;
        String role = "ROLE_USER";

        // when
        String token = tokenProvider.generateAccessToken(memberId, role);

        // then
        assertThat(tokenProvider.validateAccessToken(token)).isTrue();
        assertThat(tokenProvider.getMemberIdOfAccessToken(token)).isEqualTo(memberId);
        assertThat(tokenProvider.getRoleOfAccessToken(token)).isEqualTo(role);
    }

    @Test
    @DisplayName("Refresh Token 을 생성한 뒤, 토큰을 파싱해서 memberId와 권한 정보를 얻는다.")
    void createRefreshToken() {
        // given
        TokenProvider tokenProvider = TokenProviderFixture.create(10000, 10000, tokenService);
        Long memberId = 1L;
        String role = "ROLE_USER";

        // when
        String token = tokenProvider.generateRefreshToken(memberId, role);

        // then
        assertThat(tokenProvider.validateRefreshToken(token)).isTrue();
        assertThat(tokenProvider.getMemberIdOfRefreshToken(token)).isEqualTo(1L);
        assertThat(tokenProvider.getRoleOfRefreshToken(token)).isEqualTo("ROLE_USER");

        verify(tokenService, times(1)).saveOrUpdate(any(RefreshToken.class));
    }

    @Test
    @DisplayName("Access Token 이 만료되었으면 false 를 반환한다")
    void validateAccessTokenReturnFalse() throws InterruptedException {
        // given
        TokenProvider tokenProvider = TokenProviderFixture.create(1000, 10000, tokenService);
        Long memberId = 1L;
        String role = "ROLE_USER";

        // when
        String token = tokenProvider.generateAccessToken(memberId, role);
        Thread.sleep(1100);

        // then
        assertThat(tokenProvider.validateAccessToken(token)).isFalse();
    }

    @Test
    @DisplayName("Refresh Token 이 만료되었으면 false 를 반환한다")
    void validateRefreshTokenReturnFalse() throws InterruptedException {
        // given
        TokenProvider tokenProvider = TokenProviderFixture.create(1000, 1000, tokenService);
        Long memberId = 1L;
        String role = "ROLE_USER";

        // when
        String token = tokenProvider.generateRefreshToken(memberId, role);
        Thread.sleep(1100);

        // then
        assertThat(tokenProvider.validateRefreshToken(token)).isFalse();}
}