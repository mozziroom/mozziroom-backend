package com.hhplus.project.domain.auth;

import com.hhplus.project.domain.auth.dto.Issue;
import com.hhplus.project.domain.auth.dto.Reissue;
import com.hhplus.project.support.security.jwt.JwtAuthenticationException;
import com.hhplus.project.support.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest  {

    @Mock
    HttpServletResponse response;

    @Mock
    TokenProvider tokenProvider;

    @Mock
    TokenRepository tokenRepository;

    @InjectMocks
    TokenService tokenService;

    Long REFRESH_TOKEN_ID = 1L;
    Long MEMBER_ID = 1L;
    String ROLE = "ROLE_USER";
    String ACCESS_TOKEN = "access-token";
    String REFRESH_TOKEN = "refresh-token";

    String OLD_REFRESH_TOKEN = "old-refresh-token";

    String REISSUED_ACCESS_TOKEN = "reissued-access-token";

    @DisplayName("issue 메서드 테스트")
    @Nested
    class IssueTest {
        @DisplayName("토큰 최초 발급 시 AccessToken과 RefreshToken을 발급받고 RefreshToken은 DB에 저장한다.")
        @Test
        void issueTest_firstTime() {
            // given
            RefreshToken refreshTokenDto = RefreshToken.create(MEMBER_ID, REFRESH_TOKEN, new Date());

            when(tokenProvider.issueAccessToken(MEMBER_ID, ROLE)).thenReturn(ACCESS_TOKEN);
            when(tokenProvider.issueRefreshToken(MEMBER_ID, ROLE)).thenReturn(refreshTokenDto);
            when(tokenRepository.findRefreshToken(MEMBER_ID)).thenReturn(Optional.empty());
            doNothing().when(tokenRepository).save(refreshTokenDto);

            // when
            Issue.Info tokens = tokenService.issue(MEMBER_ID, ROLE);

            // then
            assertEquals(ACCESS_TOKEN, tokens.accessToken());
            assertEquals(REFRESH_TOKEN, tokens.refreshToken());

            verify(tokenProvider, times(1)).issueAccessToken(MEMBER_ID, ROLE);
            verify(tokenProvider, times(1)).issueRefreshToken(MEMBER_ID, ROLE);
            verify(tokenRepository, times(1)).findRefreshToken(MEMBER_ID);
            verify(tokenRepository, times(1)).save(refreshTokenDto);
        }

        @DisplayName("토큰 재발급 시 AccessToken과 RefreshToken을 발급받고, RefreshToken은 기존 정보를 업데이트하여 DB에 저장한다.")
        @Test
        void issueTest_updateRefreshToken() {
            // given
            RefreshToken newRefreshTokenDto = RefreshToken.create(MEMBER_ID, REFRESH_TOKEN, new Date());
            RefreshToken oldRefreshTokenDto = new RefreshToken(REFRESH_TOKEN_ID, MEMBER_ID, OLD_REFRESH_TOKEN, LocalDateTime.of(2025, 11, 11, 18, 0, 0));
            RefreshToken updatedRefreshTokenDto = new RefreshToken(REFRESH_TOKEN_ID, MEMBER_ID, REFRESH_TOKEN, newRefreshTokenDto.expiredAt());

            when(tokenProvider.issueAccessToken(MEMBER_ID, ROLE)).thenReturn(ACCESS_TOKEN);
            when(tokenProvider.issueRefreshToken(MEMBER_ID, ROLE)).thenReturn(newRefreshTokenDto);
            when(tokenRepository.findRefreshToken(MEMBER_ID)).thenReturn(Optional.of(oldRefreshTokenDto));
            doNothing().when(tokenRepository).save(updatedRefreshTokenDto);

            // when
            Issue.Info tokens = tokenService.issue(MEMBER_ID, ROLE);

            // then
            assertEquals(ACCESS_TOKEN, tokens.accessToken());
            assertEquals(REFRESH_TOKEN, tokens.refreshToken());

            verify(tokenProvider, times(1)).issueAccessToken(MEMBER_ID, ROLE);
            verify(tokenProvider, times(1)).issueRefreshToken(MEMBER_ID, ROLE);
            verify(tokenRepository, times(1)).findRefreshToken(MEMBER_ID);
            verify(tokenRepository, times(1)).save(updatedRefreshTokenDto);
        }
    }

    @DisplayName("reissue 메서드 테스트")
    @Nested
    class ReissueTest {
        @DisplayName("토큰 재발급 요청 시 RefreshToken이 없다면 예외를 발생한다.")
        @Test
        void reissueTest_notExistsRefreshToken() {
            // given // when // then
            Assertions.assertThatThrownBy(() -> tokenService.reissue(null, response))
                    .isInstanceOf(JwtAuthenticationException.class)
                    .hasMessage(TokenException.REFRESH_TOKEN_NOT_FOUND.getMessage());
        }

        @DisplayName("토큰 재발급 요청 시 RefreshToken이 만료되었으면 예외를 발생한다.")
        @Test
        void reissueTest_expiredRefreshToken() {
            // given
            when(tokenProvider.isInvalidateRefreshToken(REFRESH_TOKEN)).thenReturn(Boolean.TRUE);

            // when // then
            Assertions.assertThatThrownBy(() -> tokenService.reissue(REFRESH_TOKEN, response))
                    .isInstanceOf(JwtAuthenticationException.class)
                    .hasMessage(TokenException.REFRESH_TOKEN_EXPIRED.getMessage());

            verify(tokenProvider, times(0)).getMemberIdOfRefreshToken(anyString());
            verify(tokenProvider, times(0)).getRoleOfRefreshToken(anyString());
            verify(tokenProvider, times(0)).issueAccessToken(anyLong(), anyString());
        }

        @DisplayName("토큰 재발급 요청 시 RefreshToken이 존재하고 만료되지 않았으면 AccessToken을 재발급한다.")
        @Test
        void reissueTest_reissueAccessToken() {
            // given
            when(tokenProvider.isInvalidateRefreshToken(REFRESH_TOKEN)).thenReturn(Boolean.FALSE);

            when(tokenProvider.getMemberIdOfRefreshToken(REFRESH_TOKEN)).thenReturn(MEMBER_ID);
            when(tokenProvider.getRoleOfRefreshToken(REFRESH_TOKEN)).thenReturn(ROLE);
            when(tokenProvider.issueAccessToken(MEMBER_ID, ROLE)).thenReturn(REISSUED_ACCESS_TOKEN);

            // when
            Reissue.Info info = tokenService.reissue(REFRESH_TOKEN, response);

            // then
            assertEquals(REISSUED_ACCESS_TOKEN, info.accessToken());

            verify(tokenProvider, times(1)).getMemberIdOfRefreshToken(REFRESH_TOKEN);
            verify(tokenProvider, times(1)).getRoleOfRefreshToken(REFRESH_TOKEN);
            verify(tokenProvider, times(1)).issueAccessToken(MEMBER_ID, ROLE);
        }
    }
}