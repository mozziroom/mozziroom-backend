package com.hhplus.project.support.security.oauth2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2UserInfoFactoryTest {

    OAuth2UserInfoFactory oAuth2UserInfoFactory = new OAuth2UserInfoFactory();

    @ParameterizedTest(name = "{0} ProviderType으로 getOAuth2UserInfo 호출 시 {1} 인스턴스를 반환한다.")
    @MethodSource("providerTypeAndUserInfoClass")
    void getOAuth2UserInfoByProviderType(ProviderType providerType, Class<?> expectedClass) {
        // given // when
        OAuth2UserInfo userInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(providerType, Map.of());

        // then
        assertThat(userInfo).isInstanceOf(expectedClass);
    }

    private static Stream<Arguments> providerTypeAndUserInfoClass() {
        return Stream.of(
                Arguments.of(ProviderType.GOOGLE, GoogleOAuth2UserInfo.class),
                Arguments.of(ProviderType.NAVER, NaverOAuth2UserInfo.class),
                Arguments.of(ProviderType.KAKAO, KakaoOAuth2UserInfo.class)
        );
    }
}