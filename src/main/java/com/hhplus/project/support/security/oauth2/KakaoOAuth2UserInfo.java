package com.hhplus.project.support.security.oauth2;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return get("kakao_account", "profile", "nickname").toString();
    }

    @Override
    public String getNickname() {
        return get("kakao_account", "profile", "nickname").toString();
    }

    @Override
    public String getProfileImgPath() {
        return get("kakao_account", "profile", "profile_image_url").toString();
    }

    @Override
    public String getEmail() {
        return get("kakao_account", "email").toString();
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.KAKAO;
    }

    @Override
    public String getProviderId() {
        return get("id").toString();
    }
}
