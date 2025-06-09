package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new BaseException(MemberException.PROVIDER_TYPE_NOT_FOUND);
        };
    }
}
