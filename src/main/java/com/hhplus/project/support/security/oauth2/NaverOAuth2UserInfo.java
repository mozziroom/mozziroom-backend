package com.hhplus.project.support.security.oauth2;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return get("response", "name").toString();
    }

    @Override
    public String getNickname() {
        return get("response", "nickname").toString();
    }

    @Override
    public String getProfileImgPath() {
        return get("response", "profile_image").toString();
    }

    @Override
    public String getEmail() {
        return get("response", "email").toString();
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.NAVER;
    }

    @Override
    public String getProviderId() {
        return get("response", "id").toString();
    }
}
