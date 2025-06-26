package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.auth.TokenService;
import com.hhplus.project.domain.auth.dto.Issue;
import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2SuccessHandlerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    Authentication authentication;

    @Mock
    TokenService tokenService;

    @Mock
    CustomOAuth2User oAuth2User;

    @InjectMocks
    OAuth2SuccessHandler oAuth2SuccessHandler;

    @Test
    @DisplayName("oauth2 로그인 성공 후 successHandler 실행 시 JWT 토큰을 발급받고 response를 세팅한다")
    void oauth2SuccessHandlerTest_Success() throws IOException {
        // given
        Long memberId = 1L;
        String role = "ROLE_USER";
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        Long refreshExpirationSeconds = 1000L;

        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getMemberId()).thenReturn(memberId);
        Collection<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));
        doReturn(authorities).when(oAuth2User).getAuthorities();

        when(tokenService.issue(memberId, role)).thenReturn(new Issue.Info(accessToken, refreshToken));
        when(tokenService.getRefreshExpirationSeconds()).thenReturn(refreshExpirationSeconds);

        // void 메서드 스터빙
        doNothing().when(response).sendRedirect(anyString());
        doNothing().when(response).setHeader(anyString(), anyString());

        // when
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(tokenService, times(1)).issue(memberId, role);
        verify(tokenService, times(1)).getRefreshExpirationSeconds();
        verify(response, times(1)).setHeader(anyString(), contains(refreshToken));
        verify(response, times(1)).sendRedirect(contains(accessToken));
    }

    @Test
    @DisplayName("oauth2 로그인 성공 후 successHandler 실행 시 권한 정보가 없으면 예외를 발생한다.")
    void oauth2SuccessHandlerTest_Fail() throws IOException {
        // given
        Long memberId = 1L;

        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getMemberId()).thenReturn(memberId);

        // when // then
        assertThatThrownBy(() -> oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication))
                .isInstanceOf(BaseException.class)
                .hasMessage(MemberException.ROLE_NOT_FOUND.getMessage());
        verify(tokenService, times(0)).issue(anyLong(), anyString());
        verify(tokenService, times(0)).getRefreshExpirationSeconds();
        verify(response, times(0)).setHeader(anyString(), anyString());
        verify(response, times(0)).sendRedirect(anyString());
    }
}